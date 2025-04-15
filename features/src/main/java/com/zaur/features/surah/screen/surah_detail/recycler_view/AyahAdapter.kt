package com.zaur.features.surah.screen.surah_detail.recycler_view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.text.BidiFormatter
import androidx.recyclerview.widget.RecyclerView
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.features.R

class AyahAdapter(
    private val ayahs: List<Ayah>,
    private val translations: List<com.zaur.domain.al_quran_cloud.models.translate.Ayah>,
    private val chapterNumber: Int,
    private val currentAyah: Int,
    private val soundIsActive: Boolean,
    private val onSoundClick: (Int) -> Unit
) : RecyclerView.Adapter<AyahAdapter.AyahViewHolder>() {

    inner class AyahViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number: TextView = view.findViewById(R.id.ayah_number)
        val arabic: TextView = view.findViewById(R.id.ayah_arabic)
        val translation: TextView = view.findViewById(R.id.ayah_translation)
        val soundIcon: ImageView = view.findViewById(R.id.sound_icon)
        val card: CardView = view.findViewById(R.id.card_ayah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayah, parent, false)
        return AyahViewHolder(view)
    }

    override fun getItemCount(): Int = ayahs.size + if (chapterNumber != 9) 1 else 0

    override fun onBindViewHolder(holder: AyahViewHolder, position: Int) {
        if (chapterNumber != 9 && position == 0) {
            holder.arabic.text = "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيمِ"
            holder.translation.text = ""
            holder.number.text = ""
            holder.soundIcon.visibility = View.GONE
            return
        }

        val index = if (chapterNumber != 9) position - 1 else position
        val ayah = ayahs[index]
        val translation = translations.getOrNull(index)?.text ?: "Перевод отсутствует"

        var arabicText = ayah.text
        if (index == 0 && chapterNumber != 9) {
            arabicText = arabicText.removePrefix("بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ")
                .trimStart(' ', '،', '\n')
        }

        holder.number.text = ayah.numberInSurah.toString()
        holder.arabic.text = BidiFormatter.getInstance().unicodeWrap(arabicText)
        holder.translation.text = translation

        if (soundIsActive && currentAyah == ayah.numberInSurah.toInt()) {
            holder.soundIcon.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.teal_700
                )
            )
            holder.card.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.highlight_bg
                )
            )
        } else {
            holder.soundIcon.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.gray
                )
            )
            holder.card.setCardBackgroundColor(Color.TRANSPARENT)
        }

        holder.soundIcon.setOnClickListener {
            onSoundClick(ayah.numberInSurah.toInt())
        }
    }
}