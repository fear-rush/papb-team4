package com.example.movieapp.database

// Database for room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.model.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

//class MovieDatabaseCallback(
//    private val scope: CoroutineScope,
//    private val resources: Resources
//): RoomDatabase.Callback() {
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(db: SupportSQLiteDatabase) {
//        super.onCreate(db)
//        INSTANCE?.let { database ->
//            scope.launch {
//                populateDatabase(database.movieDao(), resources)
//            }
//        }
//    }
//    private fun prePopulateDatabase(movieDao: MovieDao){
//
//    }
//}
