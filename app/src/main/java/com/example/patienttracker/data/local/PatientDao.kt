package com.example.patienttracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.patienttracker.domain.model.Patient
import com.example.patienttracker.util.Constraints.PATIENT_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Upsert
    suspend fun addOrUpdatePatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)

    @Query("SELECT * FROM $PATIENT_TABLE WHERE patientId = :patientId")
    suspend fun getPatientById(patientId: Int): Patient?

    @Query("SELECT * FROM $PATIENT_TABLE")
    fun getAllPatients(): Flow<List<Patient>>

}