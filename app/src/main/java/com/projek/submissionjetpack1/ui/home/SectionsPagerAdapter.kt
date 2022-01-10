package com.projek.submissionjetpack1.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.projek.submissionjetpack1.R
import com.projek.submissionjetpack1.ui.movies.MoviesFragment
import com.projek.submissionjetpack1.ui.tvShow.TvShowFragment

@Suppress("DEPRECATION")
class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie, R.string.tv_show)
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment()
            1 -> TvShowFragment()
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

}