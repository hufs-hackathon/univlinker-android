package com.practice.farmate_android.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.MentorAllItem
import com.practice.farmate_android.data.MentorItem
import com.practice.farmate_android.data.MentorServices
import com.practice.farmate_android.databinding.FragmentMentoringBinding
import com.practice.farmate_android.ui.adapter.MentorItemRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MentoringFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MentoringFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentMentoringBinding
    private var mentoStatus = 0 // 0이면 멘토, 1이면 멘티
    private var interest = "개발"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMentoringBinding.inflate(layoutInflater, container, false)
//        binding.nestedScrollView.isFillViewport = true
        return binding.root


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MentoringFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MentoringFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = SharedPreferencesManager.getInstance(requireContext()).getLong("userId", 0L)

        if (binding.chipVocation.isChecked) {
            interest = "취업"
        } else if (binding.chipDeveloper.isChecked) {
            interest = "개발"
        } else {
            interest = "언어"
        }
        Log.d("checked", "$interest")

        binding.chipGroupInterests.setOnCheckedStateChangeListener { group, checkedIds ->
            if (binding.chipVocation.isChecked) {
                interest = "취업"
            } else if (binding.chipDeveloper.isChecked) {
                interest = "개발"
            } else {
                interest = "언어"
            }

            RetrofitClient.createRetorfitClient().getMentorServices(userId, interest)
                .enqueue(object : Callback<List<MentorAllItem>> {
                    override fun onResponse(
                        call: Call<List<MentorAllItem>>,
                        response: Response<List<MentorAllItem>>
                    ) {
                        Log.d("카테고리 get 성공", "${response.body()}")
                        val mentorItemRVAdapter = MentorItemRVAdapter(requireContext(), response.body())
                        binding.rvMentors.adapter = mentorItemRVAdapter
                        binding.rvMentors.layoutManager = LinearLayoutManager(requireContext())

                    }

                    override fun onFailure(call: Call<List<MentorAllItem>>, t: Throwable) {
                        Log.d("카테고리 get 실패", "${t.message}")
                    }
                })

            Log.d("checked", "$interest")
        }

        binding.tvToolbarMento.setOnClickListener {
            mentoStatus = 0
            binding.tvToolbarMento.setTextColor(Color.BLACK)
            binding.tvToolbarMentee.setTextColor(Color.GRAY)
            Log.d("checked", "$mentoStatus")

            RetrofitClient.createRetorfitClient().getMentorServices(userId, interest)
                .enqueue(object : Callback<List<MentorAllItem>> {
                    override fun onResponse(
                        call: Call<List<MentorAllItem>>,
                        response: Response<List<MentorAllItem>>
                    ) {
                        Log.d("카테고리 get 성공", "${response.body()}")
                        val mentorItemRVAdapter = MentorItemRVAdapter(requireContext(), response.body())
                        binding.rvMentors.adapter = mentorItemRVAdapter
                        binding.rvMentors.layoutManager = LinearLayoutManager(requireContext())

                    }

                    override fun onFailure(call: Call<List<MentorAllItem>>, t: Throwable) {
                        Log.d("카테고리 get 실패", "${t.message}")
                    }
                })
        }

        binding.tvToolbarMentee.setOnClickListener {
            mentoStatus = 1
            binding.tvToolbarMentee.setTextColor(Color.BLACK)
            binding.tvToolbarMento.setTextColor(Color.GRAY)
            Log.d("checked", "$mentoStatus")

            RetrofitClient.createRetorfitClient().getMentorServices(userId, interest)
                .enqueue(object : Callback<List<MentorAllItem>> {
                    override fun onResponse(
                        call: Call<List<MentorAllItem>>,
                        response: Response<List<MentorAllItem>>
                    ) {
                        Log.d("카테고리 get 성공", "${response.body()}")
                        val mentorItemRVAdapter = MentorItemRVAdapter(requireContext(), response.body())
                        binding.rvMentors.adapter = mentorItemRVAdapter
                        binding.rvMentors.layoutManager = LinearLayoutManager(requireContext())

                    }

                    override fun onFailure(call: Call<List<MentorAllItem>>, t: Throwable) {
                        Log.d("카테고리 get 실패", "${t.message}")
                    }
                })
        }

        binding.btnWriteService.setOnClickListener {
            if (mentoStatus == 0) {
                val intent = Intent(requireContext(), WriteMentorActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(requireContext(), WriteMenteeActivity::class.java)
                startActivity(intent)
            }
        }



        if (userId != 0L) {
            Log.d("interest", "${interest}")
            RetrofitClient.createRetorfitClient().getMentorServices(userId, interest)
                .enqueue(object : Callback<List<MentorAllItem>> {
                    override fun onResponse(
                        call: Call<List<MentorAllItem>>,
                        response: Response<List<MentorAllItem>>
                    ) {
                        if (response.body()!!.size > 0) {
                            Log.d("카테고리 get 성공", "${response.body()}")
                            val mentorItemRVAdapter = MentorItemRVAdapter(requireContext(), response.body())
                            binding.rvMentors.adapter = mentorItemRVAdapter
                            binding.rvMentors.layoutManager = LinearLayoutManager(requireContext())

                        }

                    }

                    override fun onFailure(call: Call<List<MentorAllItem>>, t: Throwable) {
                        Log.d("카테고리 get 실패", "${t.message}")
                    }
                })
        }



    }
}