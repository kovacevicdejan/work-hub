package com.example.workhub.data.repository

import com.example.workhub.data.retrofit.WorkHubApi
import com.example.workhub.data.retrofit.models.Job
import com.example.workhub.data.retrofit.models.Option
import com.example.workhub.data.retrofit.models.Post
import com.example.workhub.data.retrofit.requests.DeleteJobRequest
import com.example.workhub.data.retrofit.requests.NewJobRequest
import com.example.workhub.data.retrofit.requests.NewPostRequest
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val workHubApi: WorkHubApi
) {
    suspend fun newJob(newJobRequest: NewJobRequest) {
        workHubApi.newJob(newJobRequest = newJobRequest)
    }

    suspend fun getPageJobs(page: String): List<Job> {
        return workHubApi.getPageJobs(page = page)
    }

    suspend fun deleteJob(job_id: String) {
        val deleteJobRequest = DeleteJobRequest(job_id = job_id)

        workHubApi.deleteJob(deleteJobRequest = deleteJobRequest)
    }
}
