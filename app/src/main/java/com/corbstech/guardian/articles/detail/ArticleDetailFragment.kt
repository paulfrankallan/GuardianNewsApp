package com.corbstech.guardian.articles.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.corbstech.guardian.R
import com.corbstech.guardian.databinding.ArticleDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private lateinit var binding: ArticleDetailFragmentBinding
    private val viewModel: ArticleDetailViewModel by viewModels()
    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.syncData(args.articleUrl)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArticleDetailFragmentBinding.inflate(inflater, container, false)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        binding.articleDetailFavouriteIcon.setOnClickListener {
            viewModel.onFavouriteToggled()
        }
        binding.articleDetailBackIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun render(state: ArticleDetailState) {
        with(binding) {
            articleDetailHeadline.text = state.article?.title
            articleDetailBody.text = Jsoup.parse(state.article?.body ?: "").text()
            state.article?.thumbnail?.let {
                Glide.with(requireContext()).load(it).into(articleDetailImage)
            }
            articleDetailFavouriteIcon.setImageResource(
                if(state.favourite) R.drawable.ic_fav_on else R.drawable.ic_fav_off
            )
        }
    }
}
