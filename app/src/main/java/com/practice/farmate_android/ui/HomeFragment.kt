package com.practice.farmate_android.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.practice.farmate_android.R
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.AllActivities
import com.practice.farmate_android.databinding.FragmentHomeBinding
import com.practice.farmate_android.databinding.FragmentMentoringBinding
import com.practice.farmate_android.ui.adapter.ActivityItemsRVAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var binding: FragmentHomeBinding
private var mentoStatus = 0 // 0이면 멘토, 1이면 멘티
private var interest = "동아리" // 0 취업 1 개발자 2 언어

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
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
            interest = "동아리"
        } else if (binding.chipDeveloper.isChecked) {
            interest = "공모전"
        } else {
            interest = "대외활동"
        }
        Log.d("checked", "$interest")

        binding.chipGroupInterests.setOnCheckedStateChangeListener { group, checkedIds ->
            if (binding.chipVocation.isChecked) {
                interest = "동아리"
                RetrofitClient.createRetorfitClient().getAllData(userId)
                    .enqueue(object : Callback<List<AllActivities>> {
                        override fun onResponse(
                            call: Call<List<AllActivities>>,
                            response: Response<List<AllActivities>>
                        ) {
                            if(response.code() == 200) {
                                Log.d("성공", "${response.body()}")
                                if (interest == "동아리") {
                                    val result = response.body()?.get(0)

                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                } else if (interest == "공모전") {
                                    val result = response.body()?.get(1)
                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                } else {
                                    val result = response.body()?.get(2)
                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                }
                            }

                        }

                        override fun onFailure(call: Call<List<AllActivities>>, t: Throwable) {
                            Log.d("실패", "${t.message}")
                        }

                    })
            } else if (binding.chipDeveloper.isChecked) {
                interest = "공모전"
                RetrofitClient.createRetorfitClient().getAllData(userId)
                    .enqueue(object : Callback<List<AllActivities>> {
                        override fun onResponse(
                            call: Call<List<AllActivities>>,
                            response: Response<List<AllActivities>>
                        ) {
                            if(response.code() == 200) {
                                Log.d("성공", "${response.body()}")
                                if (interest == "동아리") {
                                    val result = response.body()?.get(0)

                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                } else if (interest == "공모전") {
                                    val result = response.body()?.get(1)
                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                } else {
                                    val result = response.body()?.get(2)
                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                }
                            }

                        }

                        override fun onFailure(call: Call<List<AllActivities>>, t: Throwable) {
                            Log.d("실패", "${t.message}")
                        }

                    })

            } else {
                interest = "대외활동"
                RetrofitClient.createRetorfitClient().getAllData(userId)
                    .enqueue(object : Callback<List<AllActivities>> {
                        override fun onResponse(
                            call: Call<List<AllActivities>>,
                            response: Response<List<AllActivities>>
                        ) {
                            if(response.code() == 200) {
                                Log.d("성공", "${response.body()}")
                                if (interest == "동아리") {
                                    val result = response.body()?.get(0)

                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                } else if (interest == "공모전") {
                                    val result = response.body()?.get(1)
                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                } else {
                                    val result = response.body()?.get(2)
                                    val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                                    binding.rvServices.adapter = rvAdapter
                                    binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                                }
                            }

                        }

                        override fun onFailure(call: Call<List<AllActivities>>, t: Throwable) {
                            Log.d("실패", "${t.message}")
                        }

                    })
            }

            Log.d("checked", "$interest")
        }


        RetrofitClient.createRetorfitClient().getAllData(userId)
            .enqueue(object : Callback<List<AllActivities>> {
                override fun onResponse(
                    call: Call<List<AllActivities>>,
                    response: Response<List<AllActivities>>
                ) {
                    if(response.code() == 200) {
                        Log.d("성공", "${response.body()}")
                        if (interest == "동아리") {
                            val result = response.body()?.get(0)

                            val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                            binding.rvServices.adapter = rvAdapter
                            binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                        } else if (interest == "공모전") {
                            val result = response.body()?.get(1)
                            val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                            binding.rvServices.adapter = rvAdapter
                            binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                        } else {
                            val result = response.body()?.get(2)
                            val rvAdapter = ActivityItemsRVAdapter(requireContext(), result!!.dtoList, userId)
                            binding.rvServices.adapter = rvAdapter
                            binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
                        }
                    }

                }

                override fun onFailure(call: Call<List<AllActivities>>, t: Throwable) {
                    Log.d("실패", "${t.message}")
                }

            })


    }
}