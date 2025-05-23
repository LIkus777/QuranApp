package com.zaur.data.downloader

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultDataSource
import com.zaur.data.network.retryWithBackoff
import com.zaur.data.room.models.ChapterAudioEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface AudioDownloader {

    fun getDownloadDirectory(): File?
    fun getAudioFile(chapterNumber: Long, verseNumber: Long, reciter: String): File?
    suspend fun downloadToCache(url: String, fileName: String): File
    suspend fun downloadAndSaveAyahs(chapterAudio: ChapterAudioEntity.Base)
    suspend fun downloadAndSaveChapter(chapterAudio: ChapterAudioEntity.Base)
    suspend fun downloadFile(
        url: String,
        localFile: File,
    )

    class Base(
        private val context: Context,
    ) : AudioDownloader {

        private val okHttpClient = OkHttpClient()
        private var downloadDirectory: File? = null

        init {
            downloadDirectory = getDownloadDirectory()
        }

        override fun getDownloadDirectory(): File? {
            val internalDir = File(context.filesDir, "QuranAudioFiles")
            if (!internalDir.exists()) internalDir.mkdirs()
            return internalDir
        }

        override suspend fun downloadAndSaveChapter(chapterAudio: ChapterAudioEntity.Base) {
            withContext(Dispatchers.IO) {
                try {
                    downloadAndSaveAyahs(chapterAudio)
                } catch (e: Exception) {
                    Log.e("AudioDownloader", "Error caching chapter: ${e.message}")
                }
            }
        }

        override suspend fun downloadAndSaveAyahs(chapterAudio: ChapterAudioEntity.Base) {
            CoroutineScope(Dispatchers.IO).launch {
                val ayahs = chapterAudio.ayahs

                for (ayah in ayahs) {
                    val localFile = File(
                        downloadDirectory,
                        "${chapterAudio.reciter}_${chapterAudio.number}_${ayah.numberInSurah}.mp3"
                    )
                    val url = ayah.audio
                    if (!localFile.exists()) {
                        try {
                            downloadFile(url, localFile)
                            Log.i("AudioDownloader", "File ${localFile.name} downloaded")
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e("AudioDownloader", "Error downloading file: ${e.message}")
                        }
                    } else {
                        Log.i("AudioDownloader", "File ${localFile.name} already exists")
                    }
                }
            }
        }

        @OptIn(UnstableApi::class)
        override suspend fun downloadFile(
            url: String,
            localFile: File,
        ) {
            withContext(Dispatchers.IO) {
                val dataSpec = DataSpec(Uri.parse(url))
                val dataSource = DefaultDataSource.Factory(context).createDataSource()

                try {
                    dataSource.open(dataSpec)
                    localFile.outputStream().use { output ->
                        val buffer = ByteArray(8 * 1024)
                        while (true) {
                            val bytesRead = dataSource.read(buffer, 0, buffer.size)
                            if (bytesRead == -1) break
                            output.write(buffer, 0, bytesRead)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    localFile.delete()
                    Log.e("AudioDownloader", "Error downloading file: ${e.message}")
                    throw e
                } finally {
                    try {
                        dataSource.close()
                    } catch (e: Exception) {
                        Log.e("AudioDownloader", "Error closing dataSource: ${e.message}")
                    }
                }
            }
        }

        override fun getAudioFile(chapterNumber: Long, verseNumber: Long, reciter: String): File? {
            val fileName = "${reciter}_${chapterNumber}_${verseNumber}.mp3"
            return downloadDirectory?.let { File(it, fileName) }
        }

        override suspend fun downloadToCache(url: String, fileName: String): File {
            val cacheFile = File(context.filesDir, fileName)
            if (cacheFile.exists()) return cacheFile

            return retryWithBackoff {
                withContext(Dispatchers.IO) {
                    val start = System.currentTimeMillis()
                    val request = Request.Builder().url(url).build()

                    okHttpClient.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            throw IOException("Failed to download file: HTTP ${response.code}")
                        }

                        val body = response.body ?: throw IOException("Empty body")

                        cacheFile.outputStream().use { output ->
                            body.byteStream().copyTo(output)
                        }

                        val time = System.currentTimeMillis() - start
                        Log.d("AudioDownloader", "Downloaded $fileName in ${time}ms")
                    }

                    return@withContext cacheFile
                }
            }
        }
    }
}