package co.arslan.amex.api

import co.arslan.amex.models.DataModelClass
import retrofit2.Response
import retrofit2.http.GET

interface CurrenciesApi {

        @GET("/v6/d4efd28fb31a242012a94c52/latest/PKR/")
        suspend fun getQuotes() : Response<DataModelClass>

}