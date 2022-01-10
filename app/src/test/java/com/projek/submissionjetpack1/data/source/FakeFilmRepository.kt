package com.projek.submissionjetpack1.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import com.projek.submissionjetpack1.data.source.local.LocalDataSource
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.data.source.local.entity.DetailFilmWithListActor
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.local.entity.GenreEntity
import com.projek.submissionjetpack1.data.source.remote.RemoteDataSource
import com.projek.submissionjetpack1.data.source.remote.response.*
import com.projek.submissionjetpack1.utils.AppExecutors
import com.projek.submissionjetpack1.utils.DataDummy
import com.projek.submissionjetpack1.vo.Resource

class FakeFilmRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : FilmDataSource {
    //all of the movie list is made on the account of themoviedb. Direcor or actor list can't be made due to restriction in themoviedb

    override fun getDetailMovie(idMovie: Int): LiveData<Resource<DetailFilmWithListActor>> {

        return object : NetworkBoundResouces<DetailFilmWithListActor, DetailMovie>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailFilmWithListActor> {
                return localDataSource.getFilmWithAllActors(idMovie.toString())
            }

            override fun shouldFetch(data: DetailFilmWithListActor?): Boolean {
                var isiGenre = data?.mFilmEntity?.genres?.get(0)
                var isInt = isiGenre?.code
                var hasil = false
                if (isInt != null) {
                    hasil = data == null || data.mCasterEntity.isEmpty() || (isInt in 48..57)
                }
                return hasil
            }

            override fun createCall(): LiveData<ApiResponse<DetailMovie>> {
                return remoteDataSource.getDetailedMovie(idMovie)
            }

            //simpan data dari DetailMovie ke FilmEntity
            override fun saveCallResult(data: DetailMovie) {
                //simpan sama update movie juga, karena listMovie ngeluarin genreId yang ArrayList<Int>
                lateinit var movieResult: FilmEntity

                val releaseDateArray =
                    data.releaseDate.split("-")
                val releaseDateFinal =
                    "${releaseDateArray[2]}/${releaseDateArray[1]}/${releaseDateArray[0]}"
                val genres = data.genres
                var genreFinal = ""
                for (genre in genres) {
                    genreFinal = if (genreFinal == "") {
                        "${genreFinal}${genre.name}"
                    } else {
                        "${genreFinal},${genre.name}"
                    }

                }
                val voteAverageFinal = data.voteAverage / 2

                val finalFilmResponse = FilmEntity(
                    data.id.toString(),
                    data.originalTitle,
                    "$voteAverageFinal",
                    releaseDateFinal,
                    data.overview,
                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${data.posterPath}",
                    genreFinal,
                    "Movie",
                    false
                )
                localDataSource.updateFilm(finalFilmResponse, false)

                val actorsCasting = DataDummy.getActor()
                val directors = DataDummy.getDirector()

                val actorCaster = actorsCasting.firstOrNull { it.idFilm == idMovie.toString() }
                val directorCaster = directors.firstOrNull { it.idFilm == idMovie.toString() }
                val credits = data.credits
                val casters = credits.cast
                val crews = credits.crew

                val arrayListCaster = ArrayList<Casting>()
                val arrayListActor = ArrayList<Casting>()
                val arrayListDirector = ArrayList<Casting>()

                for (caster in casters) {
                    Log.d("ListActor", "${caster.id}")
                    if (caster.id.toString() == directorCaster?.idCaster) {
                        if (arrayListDirector.isEmpty()) {
                            arrayListDirector.add(
                                Casting(
                                    caster.id.toString(),
                                    caster.name,
                                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${caster.profilePath}",
                                    "-",
                                    "-",
                                    "-",
                                    "Director",
                                    idMovie.toString()
                                )
                            )
                        }

                    } else if (caster.id.toString() == actorCaster?.idCaster) {
                        if (arrayListActor.isEmpty()) {
                            arrayListActor.add(
                                Casting(
                                    caster.id.toString(),
                                    caster.name,
                                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${caster.profilePath}",
                                    "-",
                                    "-",
                                    "-",
                                    "Actor",
                                    idMovie.toString()
                                )
                            )
                        }

                    }
                }

                for (crew in crews) {
                    Log.d("ListActorCrew", "${crew.id}")
                    if (crew.id.toString() == directorCaster?.idCaster) {
                        if (arrayListDirector.isEmpty()) {
                            arrayListDirector.add(
                                Casting(
                                    crew.id.toString(),
                                    crew.name,
                                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${crew.profilePath}",
                                    "-",
                                    "-",
                                    "-",
                                    "Director",
                                    idMovie.toString()
                                )
                            )
                        }
                    } else if (crew.id.toString() == actorCaster?.idCaster) {
                        if (arrayListActor.isEmpty()) {
                            arrayListActor.add(
                                Casting(
                                    crew.id.toString(),
                                    crew.name,
                                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${crew.profilePath}",
                                    "-",
                                    "-",
                                    "-",
                                    "Actor",
                                    idMovie.toString()
                                )
                            )

                        }
                    }
                }
                arrayListCaster.addAll(arrayListDirector)
                arrayListCaster.addAll(arrayListActor)

                localDataSource.insertCasters(arrayListCaster)
            }
        }.asLiveData()
    }

    override fun getTvShowDetailedInfo(idTvShow: Int): LiveData<Resource<DetailFilmWithListActor>> {
        return object : NetworkBoundResouces<DetailFilmWithListActor, DetailTvShow>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<DetailTvShow>> {
                return remoteDataSource.getDetailedTvShowRetrofit(idTvShow)
            }

            override fun shouldFetch(data: DetailFilmWithListActor?): Boolean {
                var isiGenre = data?.mFilmEntity?.genres?.get(0)
                var isInt = isiGenre?.code
                var hasil = false
                if (isInt != null) {
                    hasil = data == null || data.mCasterEntity.isEmpty() || (isInt in 48..57)
                }
                return hasil
            }

            override fun saveCallResult(data: DetailTvShow) {
                //simpan sama update movie juga, karena listMovie ngeluarin genreId yang ArrayList<Int>
                lateinit var movieResult: FilmEntity

                val releaseDateArray =
                    data.firstAirDate.split("-")
                val releaseDateFinal =
                    "${releaseDateArray[2]}/${releaseDateArray[1]}/${releaseDateArray[0]}"
                val genres = data.genres
                var genreFinal = ""
                for (genre in genres) {
                    genreFinal = if (genreFinal == "") {
                        "${genreFinal}${genre.name}"
                    } else {
                        "${genreFinal},${genre.name}"
                    }

                }
                val voteAverageFinal = data.voteAverage / 2

                val finalFilmResponse = FilmEntity(
                    data.id.toString(),
                    data.name,
                    "$voteAverageFinal",
                    releaseDateFinal,
                    data.overview,
                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${data.posterPath}",
                    genreFinal,
                    "TvShow",
                    false
                )
                localDataSource.updateFilm(finalFilmResponse, false)

                val actorsCasting = DataDummy.getActor()
                val directors = DataDummy.getDirector()

                val actorCaster = actorsCasting.firstOrNull { it.idFilm == idTvShow.toString() }
                val directorCaster = directors.firstOrNull { it.idFilm == idTvShow.toString() }
                val credits = data.aggregateCredits
                val casters = credits.cast
                val crews = credits.crew

                val arrayListCaster = ArrayList<Casting>()
                val arrayListActor = ArrayList<Casting>()
                val arrayListDirector = ArrayList<Casting>()

                for (caster in casters) {
                    Log.d("ListActor", "${caster.id}")
                    if (caster.id.toString() == directorCaster?.idCaster) {
                        if (arrayListDirector.isEmpty()) {
                            arrayListDirector.add(
                                Casting(
                                    caster.id.toString(),
                                    caster.name,
                                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${caster.profilePath}",
                                    "-",
                                    "-",
                                    "-",
                                    "Director",
                                    idTvShow.toString()
                                )
                            )
                        }
                    } else if (caster.id.toString() == actorCaster?.idCaster) {
                        if (arrayListActor.isEmpty()) {
                            arrayListActor.add(
                                Casting(
                                    caster.id.toString(),
                                    caster.name,
                                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${caster.profilePath}",
                                    "-",
                                    "-",
                                    "-",
                                    "Actor",
                                    idTvShow.toString()
                                )
                            )
                        }

                    }
                }

                for (crew in crews) {
                    Log.d("ListActorCrew", "${crew.id}")
                    if (crew.id.toString() == directorCaster?.idCaster) {
                        if (arrayListDirector.isEmpty()) {
                            arrayListDirector.add(
                                Casting(
                                    crew.id.toString(),
                                    crew.name,
                                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${crew.profilePath}",
                                    "-",
                                    "-",
                                    "-",
                                    "Director",
                                    idTvShow.toString()
                                )
                            )
                        }
                    } else if (crew.id.toString() == actorCaster?.idCaster) {
                        if (arrayListActor.isEmpty()) {
                            arrayListActor.add(
                                Casting(
                                    crew.id.toString(),
                                    crew.name,
                                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${crew.profilePath}",
                                    "-",
                                    "-",
                                    "-",
                                    "Actor",
                                    idTvShow.toString()
                                )
                            )

                        }
                    }
                }

                arrayListCaster.addAll(arrayListDirector)
                arrayListCaster.addAll(arrayListActor)

                localDataSource.insertCasters(arrayListCaster)
            }

            override fun loadFromDB(): LiveData<DetailFilmWithListActor> {
                return localDataSource.getFilmWithAllActors(idTvShow.toString())
            }

        }.asLiveData()
    }

    override fun getDetailedCaster(idCaster: Int, idFilm: String): LiveData<Resource<Casting>> {
        return object : NetworkBoundResouces<Casting, CasterResponse>(appExecutors) {

            override fun createCall(): LiveData<ApiResponse<CasterResponse>> {
                return remoteDataSource.getDetailCaster(idCaster)
            }

            override fun saveCallResult(data: CasterResponse) {

                val dateOfBirth = data.birthday?.split("-")
                val dateResult =
                    "${dateOfBirth?.get(2)}/${dateOfBirth?.get(1)}/${dateOfBirth?.get(0)}"
                val job = data.knownForDepartment
                val imageCaster =
                    "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${data.profilePath}"
                val typeFin: String = if (job == "Acting") {
                    "Actor"
                } else {
                    "Director"
                }
                val finalCasting =
                    Casting(
                        data.id.toString(),
                        data.name,
                        imageCaster,
                        data.biography,
                        dateResult,
                        data.placeOfBirth ?: "-",
                        typeFin,
                        idFilm
                    )
                localDataSource.updateCaster(finalCasting)
            }

            //actor diupdate saat kosong, sama isi dalamnya placeOfbirth sama dateOfBirth kosong karena
            //yang kosong ini didapat dari detail crew
            override fun shouldFetch(data: Casting?): Boolean {
                return data == null || data.placeOfBirth == "-" || data.dateOfBirth == "-"
            }

            override fun loadFromDB(): LiveData<Casting> {
                return localDataSource.getCaster(idCaster.toString())
            }

        }.asLiveData()
    }

    override fun filmIdentification(
        idFilm: Int,
        filmType: String
    ): LiveData<Resource<DetailFilmWithListActor>> {
        return if (filmType == "Movie") {
            getDetailMovie(idFilm)
        } else {
            getTvShowDetailedInfo(idFilm)
        }
    }

    //ambil listTvShow pertama kali saat sudah ilang tvShow from database
    override fun getListTvShow(): LiveData<Resource<List<FilmEntity>>> {

        return object : NetworkBoundResouces<List<FilmEntity>, List<TvShowList>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<List<TvShowList>>> {
                return remoteDataSource.getTvShowList()
            }

            override fun loadFromDB(): LiveData<List<FilmEntity>> {
                return localDataSource.getAllFilmEntities()
            }

            override fun shouldFetch(data: List<FilmEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun saveCallResult(data: List<TvShowList>) {
                val tvShowFinal = ArrayList<FilmEntity>()
                for (item in data) {
                    val genreIds = item.genreIds
                    var listGenres = ""
                    for (genreId in genreIds) {
                        listGenres = if (listGenres == "") {
                            "${listGenres}${genreId}"
                        } else {
                            "${listGenres},${genreId}"
                        }
                    }
                    val firstAir = item.firstAirDate.split("-")
                    val release = "${firstAir[2]}/${firstAir[1]}/${firstAir[0]}"
                    tvShowFinal.add(
                        FilmEntity(
                            item.id.toString(),
                            item.originalName,
                            "${item.voteAverage / 2}",
                            release,
                            item.overview,
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${item.posterPath}",
                            listGenres,
                            "TvShow",
                            false
                        )
                    )
                }
                Log.d("MessageRepos", tvShowFinal.toString())
                localDataSource.insertFilms(tvShowFinal as List<FilmEntity>)
            }


        }.asLiveData()


    }

    //mendapatkan semua filmEntities tapi harus dipisah dengan movie
    override fun getMovieList(): LiveData<Resource<List<FilmEntity>>> {
        return object : NetworkBoundResouces<List<FilmEntity>, List<MovieList>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<List<MovieList>>> {
                return remoteDataSource.getMovieList()
            }

            override fun saveCallResult(data: List<MovieList>) {

                val movieFinal = ArrayList<FilmEntity>()

                for (item in data) {
                    val genreIds = item.genreIds
                    var listGenres = ""
                    for (genreId in genreIds) {
                        listGenres = if (listGenres == "") {
                            "${listGenres}${genreId}"
                        } else {
                            "${listGenres},${genreId}"
                        }
                    }
                    val firstAir = item.releaseDate.split("-")
                    val release = "${firstAir[2]}/${firstAir[1]}/${firstAir[0]}"
                    movieFinal.add(
                        FilmEntity(
                            item.id.toString(),
                            item.originalTitle,
                            "${item.voteAverage / 2}",
                            release,
                            item.overview,
                            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${item.posterPath}",
                            listGenres,
                            "Movie",
                            false
                        )
                    )
                }
                localDataSource.insertFilms(movieFinal as List<FilmEntity>)
            }

            override fun shouldFetch(data: List<FilmEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDB(): LiveData<List<FilmEntity>> {
                return localDataSource.getAllFilmEntities()
            }
        }.asLiveData()
    }


    override fun getGenresAll(typeFilm: String): LiveData<Resource<List<GenreEntity>>> {
        return object : NetworkBoundResouces<List<GenreEntity>, List<GenresItem>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<List<GenresItem>>> {
                return if (typeFilm == "Movie") {
                    remoteDataSource.getGenresMovie()
                } else {
                    remoteDataSource.getGenresTvShow()
                }
            }

            override fun shouldFetch(data: List<GenreEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDB(): LiveData<List<GenreEntity>> {
                return localDataSource.getGenres()
            }

            override fun saveCallResult(data: List<GenresItem>) {
                val genres: ArrayList<GenreEntity> = arrayListOf()
                for (genre in data) {
                    genres.add(
                        GenreEntity(
                            genre.id.toString(),
                            genre.name
                        )
                    )
                }
                localDataSource.insertGenres(genres)
            }
        }.asLiveData()
    }

    override fun getFavouritedFilm(): LiveData<List<FilmEntity>> {
        return localDataSource.getFavouriteFilm()
    }

    override fun setFavouriteFilm(film: FilmEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.updateFilm(film, state) }
    }
}