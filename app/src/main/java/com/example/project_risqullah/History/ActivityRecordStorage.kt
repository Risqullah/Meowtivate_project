package com.example.project_risqullah.History

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object ActivityRecordStorage {

    private const val PREF_NAME =
        "meowtivate_activity_records"

    private const val KEY_RECORDS =
        "records_json"

    // Mengambil seluruh catatan aktivitas
    fun getAll(context: Context): List<ActivityRecord> {

        val preferences = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )

        val savedData = preferences.getString(
            KEY_RECORDS,
            "[]"
        ) ?: "[]"

        return try {

            val jsonArray = JSONArray(savedData)

            val records = mutableListOf<ActivityRecord>()

            for (i in 0 until jsonArray.length()) {

                val item = jsonArray.optJSONObject(i)
                    ?: continue

                records.add(
                    ActivityRecord(
                        id = item.optString("id"),
                        name = item.optString("name"),
                        category = item.optString("category"),
                        amount = item.optDouble("amount"),
                        unit = item.optString("unit"),
                        createdAt = item.optLong("createdAt")
                    )
                )
            }

            records.sortedByDescending {
                it.createdAt
            }

        } catch (e: JSONException) {
            emptyList()
        }
    }

    // Menambahkan aktivitas baru
    fun add(
        context: Context,
        record: ActivityRecord
    ) {

        val records = getAll(context).toMutableList()

        records.add(record)

        val jsonArray = JSONArray()

        records
            .sortedByDescending { it.createdAt }
            .forEach { item ->

                val jsonObject = JSONObject().apply {
                    put("id", item.id)
                    put("name", item.name)
                    put("category", item.category)
                    put("amount", item.amount)
                    put("unit", item.unit)
                    put("createdAt", item.createdAt)
                }

                jsonArray.put(jsonObject)
            }

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                KEY_RECORDS,
                jsonArray.toString()
            )
            .apply()
    }

    // Menghapus seluruh catatan aktivitas
    fun clear(context: Context) {

        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
            .edit()
            .remove(KEY_RECORDS)
            .apply()
    }
}