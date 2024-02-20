package com.kashapovrush.osmtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kashapovrush.osmtest.databinding.FragmentItemProductBinding

class ItemProduct : Fragment() {

    private lateinit var binding: FragmentItemProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString(EXTRA_NAME)

        binding.name.text = name
        binding.description.text = "Описание для $name"

        when(name) {
            "Пепперони" -> { binding.image.setImageResource(R.drawable.ic_pepperoni)}
            "Маргарита" -> { binding.image.setImageResource(R.drawable.ic_margaritajpg)}
            "4 сыра" -> { binding.image.setImageResource(R.drawable.fouth_cheeze)}
            "Филадельфия" -> { binding.image.setImageResource(R.drawable.ic_filadelfia)}
            "Калифорния" -> { binding.image.setImageResource(R.drawable.ic_california)}
            "Хот унаги" -> { binding.image.setImageResource(R.drawable.ic_hot_unagi)}
            else -> { binding.image.setImageResource(R.drawable.ic_udon_with_chicken)}
        }

        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {

        const val EXTRA_NAME = "name"

        fun newInstance(name: String) = ItemProduct().apply {
            arguments = Bundle().apply {
                putString(EXTRA_NAME, name)
            }
        }
    }
}