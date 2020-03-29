package com.joinhonor.bugtracker.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.joinhonor.bugtracker.ApiRepository
import com.joinhonor.bugtracker.Bug
import com.joinhonor.bugtracker.DeleteBugResponse
import com.joinhonor.bugtracker.GetBugsResponse
import com.joinhonor.bugtracker.util.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class BugsViewModelTest {

    val application : Application = mockk(relaxed = true)
    val apiRepository: ApiRepository = mockk(relaxed = true)
    val viewModel = BugsViewModel(application, apiRepository)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // The dispatcher and the before and after are needed if we want to test ViewModelScope.Launch{}
    private val testingDispatcher = Dispatchers.Unconfined

    @Before
    fun doBeforeEach() {
        Dispatchers.setMain(testingDispatcher)
    }

    @After
    fun doAfterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetBugsListSuccess() {

        val response = GetBugsResponse(listOf(
            Bug("testId1", "testTitle1", "testDesc1", "testStatus1", "testAsignee1"),
            Bug("testId2", "testTitle2", "testDesc2", "testStatus2", "testAsignee2"),
            Bug("testId3", "testTitle3", "testDesc3", "testStatus3", "testAsignee3")))
        coEvery { apiRepository.getBugsList() } returns NetworkResult.Success(response)
        val result = runBlocking {
            viewModel.getBugsList()
        }
        Assert.assertEquals(viewModel.bugs.size, 3)
        Assert.assertNotNull(viewModel.showList.value)
    }

    @Test
    fun testAddBug() {
        viewModel.bugs = mutableListOf(
            Bug("testId1", "testTitle1", "testDesc1", "testStatus1", "testAsignee1"),
            Bug("testId2", "testTitle2", "testDesc2", "testStatus2", "testAsignee2"),
            Bug("testId3", "testTitle3", "testDesc3", "testStatus3", "testAsignee3"),
            Bug("testId4", "testTitle4", "testDesc4", "testStatus4", "testAsignee4"))
        val bug = Bug("testId5", "testTitle5", "testDesc5", "testStatus5", "testAsignee5")

        coEvery { apiRepository.addBug(any(), any()) } returns NetworkResult.Success(bug)
        runBlocking {
            viewModel.addBug("testTitle5", "testTitle5")
        }
        Assert.assertNotNull(viewModel.closeAddBugFragment.value)
        Assert.assertEquals(viewModel.bugs.size, 5)
    }

    @Test
    fun testDeleteBug() {
        val bugReport = DeleteBugResponse(true)
        viewModel.bugs = mutableListOf(
            Bug("testId1", "testTitle1", "testDesc1", "testStatus1", "testAsignee1"),
            Bug("testId2", "testTitle2", "testDesc2", "testStatus2", "testAsignee2"),
            Bug("testId3", "testTitle3", "testDesc3", "testStatus3", "testAsignee3"),
            Bug("testId4", "testTitle4", "testDesc4", "testStatus4", "testAsignee4"),
            Bug("testId5", "testTitle5", "testDesc5", "testStatus5", "testAsignee5"))

        coEvery { apiRepository.deleteBug(any()) } returns NetworkResult.Success(bugReport)
        Assert.assertEquals(viewModel.bugs.size, 5)
        runBlocking {
            viewModel.deleteBug("testId3")
            viewModel.deleteBug("testId2")
        }
        Assert.assertEquals(viewModel.bugs.size, 3)
    }

    @Test
    fun testGetBugList(){
        val response = GetBugsResponse(listOf(
            Bug("testId1", "testTitle1", "testDesc1", "testStatus1", "testAsignee1"),
            Bug("testId2", "testTitle2", "testDesc2", "testStatus2", "testAsignee2"),
            Bug("testId3", "testTitle3", "testDesc3", "testStatus3", "testAsignee3")))
        coEvery { apiRepository.getBugsList() } returns NetworkResult.Success(response)
        val result = runBlocking {
            viewModel.getBugsList()
        }
        val list = viewModel.getBugList()
        Assert.assertEquals(list.size, 3)
    }
}