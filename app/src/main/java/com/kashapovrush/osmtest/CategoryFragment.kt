package com.kashapovrush.osmtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import com.kashapovrush.osmtest.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryPizza.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, MenuFragment.newInstance("Пицца"))
                .commit()
        }

        binding.categorySushi.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, MenuFragment.newInstance("Суши"))
                .commit()
        }

        binding.categoryWok.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, MenuFragment.newInstance("WOK"))
                .commit()
        }

        binding.backBtn.setOnClickListener {
            startActivity(MainActivity.newIntent(requireContext()))
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startActivity(MainActivity.newIntent(requireContext()))
                }

            }
        )
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }
}