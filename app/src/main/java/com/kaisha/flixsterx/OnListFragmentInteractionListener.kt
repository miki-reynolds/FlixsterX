package com.kaisha.flixsterx


interface OnListFragmentInteractionListener {
    /**
     * This interface is used by the [NowPlayingMoviesRecyclerViewAdapter] to ensure
     * it has an appropriate Listener.
     *
     * In this app, it's implemented by [NowPlayingMoviesFragment]
     */
    fun onItemClick(item: NowPlayingMovie)

}