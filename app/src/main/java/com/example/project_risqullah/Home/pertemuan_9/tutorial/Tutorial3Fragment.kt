package com.example.project_risqullah.Home.pertemuan_9.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_risqullah.LoginActivity
import com.example.project_risqullah.R

class Tutorial3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_tutorial3,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnStartLogin)
            .setOnClickListener {

                startActivity(
                    Intent(
                        requireContext(),
                        LoginActivity::class.java
                    )
                )

                requireActivity().finish()
            }
    }
}