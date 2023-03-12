package com.kaisha.flixsterx

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.swiperefreshlayout.widget.CircularProgressDrawable



class NowPlayingMoviesRecyclerViewAdapter(
    private val movies: List<NowPlayingMovie>,
    private val movieListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<NowPlayingMoviesRecyclerViewAdapter.MovieViewHolder>() {
    /**
     * [RecyclerView.Adapter] that can display a [NowPlayingMovie] and makes a call to the
     * specified [OnListFragmentInteractionListener].
     */

    inner class MovieViewHolder(val movieView: View) : RecyclerView.ViewHolder(movieView) {
        var movieItem: NowPlayingMovie? = null
        // we already declare the type here, so no need to declare "as TextView,"
        // which helps compiler treat it as TV instead of a generic View - good practice.
        val movieImage: ImageView = movieView.findViewById<View>(R.id.movie_image) as ImageView
        val movieTitle: TextView = movieView.findViewById<View>(R.id.movie_title) as TextView
        val movieOverview: TextView = movieView.findViewById<View>(R.id.movie_description) as TextView

        override fun toString(): String {
            return movieTitle.toString() + " '" + movieOverview.text + "'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val orientation = parent.context.resources.configuration.orientation
        val layoutId = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            R.layout.fragment_now_playing_movie_land
        } else {
            R.layout.fragment_now_playing_movie
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.movieItem = movie
        holder.movieTitle.text = movie.title
        holder.movieOverview.text = movie.overview

//        val width = if (holder.movieView.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 1000 else ViewGroup.LayoutParams.WRAP_CONTENT
//        val height = if (holder.movieView.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 600 else 150

//        val circularProgress = CircularProgressDrawable(holder.movieView.context)
//        circularProgress.strokeWidth = 5f
//        circularProgress.centerRadius = 30f
//        circularProgress.start()

        Glide.with(holder.movieView)
            .load("https://image.tmdb.org/t/p/w500" + movie.poster)
            .centerInside()
//            .override(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            .placeholder(R.drawable.loading)
//            .error(R.drawable.imagenotfound)
            .into(holder.movieImage)

        holder.movieView.setOnClickListener {
            holder.movieItem?.let { book ->
                movieListener?.onItemClick(book)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}