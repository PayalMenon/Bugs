package com.joinhonor.bugtracker

import android.util.Log
import com.joinhonor.bugtracker.util.NetworkResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(val bugService: BugService) {

    suspend fun getBugsList() : NetworkResult<GetBugsResponse> {
        val response : Response<GetBugsResponse> = bugService.getBugs()
        return if(response.isSuccessful) {
            val body = response.body()
            body?.let {
                NetworkResult.Success(body)
            } ?: NetworkResult.Failure(response)
        } else { NetworkResult.Failure(response) }
    }

    suspend fun addBug(title: String, body: String) : NetworkResult<Bug> {
        val response : Response<Bug> = bugService.createBug(CreateBugRequest(title, body))
        return if(response.isSuccessful) {
            val body = response.body()
            body?.let {
                NetworkResult.Success(body)
            } ?: NetworkResult.Failure(response)
        } else { NetworkResult.Failure(response) }
    }

    suspend fun deleteBug(bugID: String) : NetworkResult<DeleteBugResponse> {
        val response : Response<DeleteBugResponse> = bugService.deleteBug(bugID)
        return if(response.isSuccessful) {
            val body = response.body()
            body?.let {
                NetworkResult.Success(body)
            } ?: NetworkResult.Failure(response)
        } else { NetworkResult.Failure(response) }
    }
}