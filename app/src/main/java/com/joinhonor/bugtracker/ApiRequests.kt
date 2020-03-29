@file:JvmName("ApiRequests")
package com.joinhonor.bugtracker

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

private val BASE_URL = "http://bugs.honor-projects.com:5000/api/"

/** Represents a Bug object returned by the API. */
data class Bug(val id: String, val title: String, val description: String, val status: String, val assignee: String?)

/** Represents a response from [BugService.getBugs]. */
data class GetBugsResponse(val bugs: List<Bug>)

/** Represents a response from [BugService.deleteBug]. */
data class DeleteBugResponse(val result: Boolean)

/** Holds parameters for [BugService.createBug]. */
data class CreateBugRequest(val title: String, val description: String)

/** Holds parameters for [BugService.updateBug]. */
data class UpdateBugRequest(val title: String? = null,
                            val description: String? = null,
                            val status: String? = null,
                            val assignee: String? = null)

/** Type-safe interface for interacting with the various bug API endpoints using Retrofit. */
interface BugService {
    /**
     * Fetch all bugs from the server.  Optionally filter by assignee or status.
     *
     * The response will contain a JSON object with a list of bugs, structured like the following:
     *
     * {
     *   "bugs": [
     *     {
     *       "assignee": "Ned",
     *       "description": "When the app is opened on a Tuesday, it crashes.",
     *       "id": "1",
     *       "status": "open",
     *       "title": "App crashes on Tuesdays"
     *     },
     *     {
     *       "assignee": "Camille",
     *       "description": "When the app is opened on a Monday, it freezes.",
     *       "id": "2",
     *       "status": "closed",
     *       "title": "App hangs on Mondays"
     *      }
     *   ]
     * }
     *
     * The JSON object will then be converted to a [GetBugsResponse] by Retrofit.
     */
    @GET("bugs")
    suspend fun getBugs(@Query("sort") sort: String? = null, @Query("status") status: String? = null,
                @Query("assignee") assignee: String? = null): Response<GetBugsResponse>

    /**
     * Create a new bug.  Its status will be open and it will have no assignee.
     *
     * The response will contain a JSON object describing the new bug, structured like the
     * following:
     *
     * {
     *   "assignee": null,
     *   "description": "When the app is opened on a Wednesday, it is slow.",
     *   "id": "3",
     *   "status": "open",
     *   "title": "App slow on Wednesdays"
     * }
     *
     * The JSON object will then be converted to a [Bug] by Retrofit.
     */
    @POST("bug")
    suspend fun createBug(@Body request: CreateBugRequest): Response<Bug>

    /**
     * Update an existing bug.
     *
     * Leave any of the [UpdateBugRequest] parameters null to leave them unchanged.
     *
     * The response will contain a JSON object describing the new bug, structured like the
     * following:
     *
     * {
     *   "assignee": "Justin",
     *   "description": "When the app is opened on a Wednesday, it is slow.",
     *   "id": "3",
     *   "status": "open",
     *   "title": "App slow on Wednesdays"
     * }
     *
     * The JSON object will then be converted to a [Bug] by Retrofit.
     */
    @POST("bug/{id}")
    fun updateBug(@Path("id") bugId: String, @Body request: UpdateBugRequest): Call<Bug>

    /**
     * Delete a bug.
     *
     * The response will contain a JSON object indicating the bug was successfully deleted:
     *
     * {
     *   "result": true
     * }
     *
     * The JSON object will then be converted to a [DeleteBugResponse] by Retrofit.
     */
    @DELETE("bug/{id}")
    suspend fun deleteBug(@Path("id") bugId: String): Response<DeleteBugResponse>
}

/** Creates an instance of [BugService] for making requests. */
/*fun createService(): BugService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BugService::class.java)*/
