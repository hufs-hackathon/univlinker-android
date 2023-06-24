package com.practice.farmate_android.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.practice.farmate_android.R
import com.practice.farmate_android.RetrofitClient
import com.practice.farmate_android.SharedPreferencesManager
import com.practice.farmate_android.data.Mypage
import com.practice.farmate_android.databinding.FragmentMentoringBinding
import com.practice.farmate_android.databinding.FragmentMyPageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentMyPageBinding

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
        binding = FragmentMyPageBinding.inflate(layoutInflater, container, false)
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
         * @return A new instance of fragment MyPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = SharedPreferencesManager.getInstance(requireContext()).getLong("userId", 0L)

        RetrofitClient.createRetorfitClient().getMyPage(userId)
            .enqueue(object : Callback<Mypage> {
                override fun onResponse(call: Call<Mypage>, response: Response<Mypage>) {
                    if(response.isSuccessful) {
                        val result = response.body()
                        val profileUrl = SharedPreferencesManager.getInstance(requireContext())
                            .getString("profileUrl", "null")
                        Glide.with(requireContext())
                            .load(profileUrl)
                            .into(binding.ivProfile)

                        binding.tvTagMentor.text = "멘토"
                        binding.tvName.text = result?.nickname
                        binding.tvSchoolContent.text = result?.univ
                        binding.tvMajorContent.text = result?.major
                        binding.tvEmailContent.text = result?.email
                    }
                }

                override fun onFailure(call: Call<Mypage>, t: Throwable) {
                    Log.d("실패", "${t.message}")
                }

            })
    }
}