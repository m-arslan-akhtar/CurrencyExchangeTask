package co.arslan.amex.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.arslan.amex.models.ConversionRates

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(getUserRewardPointResponse: ConversionRates)

    @Query("SELECT * FROM ConversionRates")
    fun getAllDataSet(): LiveData<ConversionRates>

}