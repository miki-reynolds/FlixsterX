package com.kaisha.flixsterx

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject


private const val API_KEY = "91277fca87661cc5c4d23d91fccfe535"


class NowPlayingMoviesFragment : Fragment(), OnListFragmentInteractionListener {
    /*
    * The class for the only fragment in the app, which contains the progress bar,
    * recyclerView, and performs the network calls to the Movie Database API.
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) : View? {
        /*
        construct the View.
        */
        val view = inflater.inflate(R.layout.fragment_now_playing_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progressBar) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.movieList) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        /*
        * Updates the RecyclerView adapter with new data - where the networking magic happens!
        */
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/now_playing",
                params,
                object : JsonHttpResponseHandler()
                {
                    // onSuccess function gets called when HTTP response status is "200 OK"
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {

                        // look for this in Logcat:
                        Log.d("NowPlayingMoviesFragment", "response successful")
//                        Log.d("JSON Result",  resultsJSON.toString())
                        // the wait for a response is over
                        progressBar.hide()
                        // parse data
                        val resultsJSON : JSONArray = json.jsonObject?.getJSONArray("results") as JSONArray
                        val moviesRawJSON : String = resultsJSON.toString()
                        // create gson to autofill data from json object for us
                        val gson = Gson()
                        val arrayBookType = object : TypeToken<List<NowPlayingMovie>>() {}.type
                        val models : List<NowPlayingMovie> = gson.fromJson(moviesRawJSON, arrayBookType)
                        recyclerView.adapter = NowPlayingMoviesRecyclerViewAdapter(models, this@NowPlayingMoviesFragment)
                    }

                    // onFailure function gets called when HTTP response status is "4XX" (eg. 401, 403, 404)
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // the wait for a response is over
                        progressBar.hide()
                        // if the error is not null, log it!
                        t?.message?.let {
                            Log.e("NowPlayingMoviesFragment", errorResponse)
                        }
                    }
                }
        ]

    }

    override fun onItemClick(item: NowPlayingMovie) {
        /*
        * show a toast when a particular book is clicked.
        */
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }
}