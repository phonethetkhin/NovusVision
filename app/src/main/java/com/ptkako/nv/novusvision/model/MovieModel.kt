package com.ptkako.nv.novusvision.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(val casts: String = "", val country: String = "",
                      val duration: String = "", val full_video_path: String = "",
                      val genres: String = "", val is_series: Boolean = false,
                      val language: String = "", val movie_cover_photo: String = "",
                      val movie_name: String = "", val movie_photo: String = "",
                      val overview: String = "", val popularity: String = "",
                      val subtitle: String = "", val trailer_video_path: String = "") : Parcelable
