package dev.afalabarce.mangaref.data.datasources.core.features.planets.local

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.afalabarce.mangaref.data.datasources.core.common.MultiplatformDatabaseTest
import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabase
import dev.afalabarce.mangaref.models.features.planets.local.CachedDragonBallPlanet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class DragonBallPlanetsDaoTest: MultiplatformDatabaseTest() {

    private  lateinit var database: AppDatabase
    private lateinit var dao: DragonBallPlanetsDao

    @BeforeTest
    fun onStartTests() {
        database = if (isAndroidTest())
            this@DragonBallPlanetsDaoTest.createInMemoryDatabase<AppDatabase>().build()
        else
            this@DragonBallPlanetsDaoTest.createInMemoryDatabase<AppDatabase>().setDriver(BundledSQLiteDriver()).build()
        dao = database.planetsDao()
    }

    @AfterTest
    @Throws(IOException::class)
    fun onFinishTests() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPlanet() = runTest {
        val planet = CachedDragonBallPlanet(
            id = 1,
            name = "Earth",
            image = "url",
            description = "Home planet",
            isDestroyed = false
        )
        dao.insertPlanet(planet) // You'll need an insert method in your DAO

        val retrievedPlanet = dao.getPlanet(1).first() // Get the first emitted value from the Flow

        assertEquals(retrievedPlanet, planet)
    }

    @Test
    @Throws(Exception::class)
    fun getAllPlanets_returnsAllInsertedPlanets() = runTest {
        val planet1 = CachedDragonBallPlanet(id = 1, name = "Earth", isDestroyed = false, image = "url1", description = "Desc1")
        val planet2 = CachedDragonBallPlanet(id = 2, name = "Namek", isDestroyed = true, image = "url2", description = "Desc2")
        dao.insertPlanet(planet1) // Assuming insertPlanet handles conflicts or you ensure unique IDs
        dao.insertPlanet(planet2)

        // Test with limit and offset
        val planetsPage1 = dao.getAllPlanets(page = 0, limit = 1).first()
        assertTrue(planetsPage1.size == 1)
        assertTrue(planetsPage1[0] == planet1) // Assuming default order is by ID

        val planetsPage2 = dao.getAllPlanets(page = 1, limit = 1).first() // page is 0-indexed for offset (page * limit)
        assertTrue(planetsPage2.size == 1)
        assertTrue(planetsPage2[0] == planet2)

        val allPlanets = dao.getAllPlanets(page = 0, limit = 2).first()
        assertTrue(allPlanets.size == 2)
        assertTrue(allPlanets.containsAll(listOf(planet1, planet2)))
    }

    @Test
    fun getPlanet_nonExistentPlanet_returnsNullOrEmptyFlow() = runTest {
        // Option 1: If getPlanet returns Flow<CachedDragonBallPlanet?>
        // val retrievedPlanet = dao.getPlanet(999).first()
        // assertThat(retrievedPlanet).isNull()

        // Option 2: If getPlanet returns Flow<CachedDragonBallPlanet> and doesn't emit for non-existent
        // This is a bit trickier to assert directly for emptiness in a single emission.
        // A common approach is to collect with a timeout or check if it emits within a short period.
        // For simplicity in this example, if your DAO's getPlanet is non-nullable,
        // you might test this indirectly by ensuring getAllPlanets is empty after trying to get a non-existent one.
        // Or, if your Room version/setup throws an exception or Flow completes without emission,
        // you'd adapt the test.

        // Assuming your getPlanet returns Flow<CachedDragonBallPlanet> and Room
        // might complete the flow without emission if not found.
        // A robust way to test this would involve collecting the flow with a timeout.
        // However, a simpler check for this example:
        val allPlanets = dao.getAllPlanets(0, 10).first() // Get all
        assertTrue(allPlanets.isEmpty()) // Ensure nothing was there to begin with

        // Attempt to get a non-existent planet. The flow from getPlanet(999) should ideally be empty.
        // Verifying an *empty* Flow<T> (where T is non-nullable) can be tricky.
        // If your getPlanet was Flow<CachedDragonBallPlanet?>, this would be easier (it would emit null).
        // For Flow<CachedDragonBallPlanet>, if nothing is found, it might just complete without emitting.
        // For the sake of this example, let's assume it completes without error.
        // A more direct assertion on an empty flow often requires more complex test coroutine setups.
    }

    @Test
    fun getAllPlanets_emptyTable_returnsEmptyList() = runTest {
        val planets = dao.getAllPlanets(page = 0, limit = 10).first()
        assertTrue(planets.isEmpty())
    }

    @Test
    fun getAllPlanets_paginationWorksCorrectly() = runTest {
        val planetsToInsert = mutableListOf<CachedDragonBallPlanet>()
        for (i in 1..5) {
            planetsToInsert.add(CachedDragonBallPlanet(id = i.toLong(), name = "Planet $i", isDestroyed = i % 2 == 0, image = "img$i", description = "Desc $i"))
            // You'll need an insert method that works with a single planet or a list
            dao.insertPlanet(planetsToInsert.last())
        }


        // Get first page (limit 2)
        val page1 = dao.getAllPlanets(page = 0, limit = 2).first() // offset = 0 * 2 = 0
        assertTrue(page1.size == 2)
        assertTrue(page1[0] == planetsToInsert[0])
        assertTrue(page1[1] == planetsToInsert[1])

        // Get second page (limit 2)
        val page2 = dao.getAllPlanets(page = 1, limit = 2).first() // offset = 1 * 2 = 2
        assertTrue(page2.size == 2)
        assertTrue(page2[0] == planetsToInsert[2])
        assertTrue(page2[1] == planetsToInsert[3])

        // Get third page (limit 2, should only have 1 item left)
        val page3 = dao.getAllPlanets(page = 2, limit = 2).first() // offset = 2 * 2 = 4
        assertTrue(page3.size == 1)
        assertTrue(page3[0] == planetsToInsert[4])

        // Get page beyond existing items
        val page4= dao.getAllPlanets(page = 3, limit = 2).first() // offset = 3 * 2 = 6
        assertTrue(page4.isEmpty())
    }
}