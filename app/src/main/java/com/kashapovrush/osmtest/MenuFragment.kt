package com.kashapovrush.osmtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kashapovrush.osmtest.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getString(EXTRA_TITLE)

        binding.titleAppBarMenu.text = category

        when (category) {
            "Пицца" -> {
                binding.image1.setImageResource(R.drawable.ic_pepperoni)
                binding.text1.text = "Пепперони"
                binding.image2.setImageResource(R.drawable.ic_margaritajpg)
                binding.text2.text = "Маргарита"
                binding.image3.setImageResource(R.drawable.fouth_cheeze)
                binding.text3.text = "4 сыра"
            }
            "Суши" -> {
                binding.image1.setImageResource(R.drawable.ic_filadelfia)
                binding.text1.text = "Филадельфия"
                binding.image2.setImageResource(R.drawable.ic_california)
                binding.text2.text = "Калифорния"
                binding.image3.setImageResource(R.drawable.ic_hot_unagi)
                binding.text3.text = "Хот унаги"
            }

            "WOK" -> {
                binding.image1.setImageResource(R.drawable.ic_udon_with_chicken)
                binding.text1.text = "Удон с курицей"
                binding.image2.setImageResource(R.drawable.ic_udon_with_chicken)
                binding.text2.text = "Удон с говядиной"
                binding.image3.setImageResource(R.drawable.ic_udon_with_chicken)
                binding.text3.text = "Удон из свинины"
            }

        }

        binding.item1.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, ItemProduct.newInstance(binding.text1.text.toString()))
                .commit()
        }

        binding.item2.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, ItemProduct.newInstance(
                    binding.text2.text.toString()
                ))
                .commit()
        }

        binding.item3.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, ItemProduct.newInstance(
                    binding.text3.text.toString()
                ))
                .commit()
        }

        binding.backBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        const val EXTRA_TITLE = "title"
        fun newInstance(title: String) = MenuFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_TITLE, title)
            }
        }
    }
}