package com.example.aplikasipertama.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.aplikasipertama.model.Student
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

// https://localhost:3000/students?page=1&size=10
class StudentPagingSource(private val firestoreDb: FirebaseFirestore) : PagingSource<QuerySnapshot, Student>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Student>): QuerySnapshot? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Student> {
        return try {
            val ref = firestoreDb.collection("students")
            // mengambil querySnapshot sekarang
            val currentPage = params.key ?: ref
                .orderBy("name")
                .limit(8)
                .get()
                .await()
            // mengambil document terakhir dari querySnapshot sekarang
            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]
            // mengambil querySnapshot selanjutnya
            val nextPage = ref
                .orderBy("name")
                .limit(8)
                .startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(
                data = currentPage.toObjects(Student::class.java),
                prevKey = null,
                nextKey = nextPage,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}