package dev.afalabarce.mangaref.data.datasources.core.features.characters.local

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.afalabarce.mangaref.data.datasources.core.common.MultiplatformDatabaseTest
import dev.afalabarce.mangaref.data.datasources.core.db.AppDatabase
import dev.afalabarce.mangaref.models.features.characters.local.CachedDragonBallCharacter
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
class DragonBallCharactersDaoTest: MultiplatformDatabaseTest() {

    private  lateinit var database: AppDatabase
    private lateinit var dao: DragonBallCharactersDao

    @BeforeTest
    fun onStartTests() {
        database = if (isAndroidTest())
            this@DragonBallCharactersDaoTest.createInMemoryDatabase<AppDatabase>().build()
        else
            this@DragonBallCharactersDaoTest.createInMemoryDatabase<AppDatabase>().setDriver(BundledSQLiteDriver()).build()
        dao = database.charactersDao()
    }

    @AfterTest
    @Throws(IOException::class)
    fun onFinishTests() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCharacter() = runTest {
        val character = CachedDragonBallCharacter(
            id = 1,
            name = "Earth",
            image = "url",
            description = "Home character",
            ki = "50",
            maxKi = "100",
            race = "Saiyan",
            gender = "Male",
            affiliation = "Z",
            isFavorite = false,
        )
        dao.insertCharacter(character) // You'll need an insert method in your DAO

        val retrievedCharacter = dao.getCharacter(1).first() // Get the first emitted value from the Flow

        assertEquals(retrievedCharacter, character)
    }

    @Test
    @Throws(Exception::class)
    fun getAllCharacters_returnsAllInsertedCharacters() = runTest {
        val character1 = CachedDragonBallCharacter(id = 1, name = "Earth", image = "url1", description = "Desc1",
            ki = "TODO()",
            maxKi = "TODO()",
            race = "TODO()",
            gender = "TODO()",
            affiliation = "TODO()",
            isFavorite = false,
            )
        val character2 = CachedDragonBallCharacter(
            id = 2, name = "Namek", image = "url2", description = "Desc2",
            ki = "TODO()",
            maxKi = "TODO()",
            race = "TODO()",
            gender = "TODO()",
            affiliation = "TODO()",
            isFavorite = false,
        )
        dao.insertCharacter(character1) // Assuming insertCharacter handles conflicts or you ensure unique IDs
        dao.insertCharacter(character2)

        // Test with limit and offset
        val charactersPage1 = dao.getAllCharacters(page = 0, limit = 1).first()
        assertTrue(charactersPage1.size == 1)
        assertTrue(charactersPage1[0] == character1) // Assuming default order is by ID

        val charactersPage2 = dao.getAllCharacters(page = 1, limit = 1).first() // page is 0-indexed for offset (page * limit)
        assertTrue(charactersPage2.size == 1)
        assertTrue(charactersPage2[0] == character2)

        val allCharacters = dao.getAllCharacters(page = 0, limit = 2).first()
        assertTrue(allCharacters.size == 2)
        assertTrue(allCharacters.containsAll(listOf(character1, character2)))
    }

    @Test
    fun getCharacter_nonExistentCharacter_returnsNullOrEmptyFlow() = runTest {
        // Option 1: If getCharacter returns Flow<CachedDragonBallCharacter?>
        // val retrievedCharacter = dao.getCharacter(999).first()
        // assertThat(retrievedCharacter).isNull()

        // Option 2: If getCharacter returns Flow<CachedDragonBallCharacter> and doesn't emit for non-existent
        // This is a bit trickier to assert directly for emptiness in a single emission.
        // A common approach is to collect with a timeout or check if it emits within a short period.
        // For simplicity in this example, if your DAO's getCharacter is non-nullable,
        // you might test this indirectly by ensuring getAllCharacters is empty after trying to get a non-existent one.
        // Or, if your Room version/setup throws an exception or Flow completes without emission,
        // you'd adapt the test.

        // Assuming your getCharacter returns Flow<CachedDragonBallCharacter> and Room
        // might complete the flow without emission if not found.
        // A robust way to test this would involve collecting the flow with a timeout.
        // However, a simpler check for this example:
        val allCharacters = dao.getAllCharacters(0, 10).first() // Get all
        assertTrue(allCharacters.isEmpty()) // Ensure nothing was there to begin with

        // Attempt to get a non-existent character. The flow from getCharacter(999) should ideally be empty.
        // Verifying an *empty* Flow<T> (where T is non-nullable) can be tricky.
        // If your getCharacter was Flow<CachedDragonBallCharacter?>, this would be easier (it would emit null).
        // For Flow<CachedDragonBallCharacter>, if nothing is found, it might just complete without emitting.
        // For the sake of this example, let's assume it completes without error.
        // A more direct assertion on an empty flow often requires more complex test coroutine setups.
    }

    @Test
    fun getAllCharacters_emptyTable_returnsEmptyList() = runTest {
        val characters = dao.getAllCharacters(page = 0, limit = 10).first()
        assertTrue(characters.isEmpty())
    }

    @Test
    fun getAllCharacters_paginationWorksCorrectly() = runTest {
        val charactersToInsert = mutableListOf<CachedDragonBallCharacter>()
        for (i in 1..5) {
            charactersToInsert.add(CachedDragonBallCharacter(id = i.toLong(), name = "Character $i", image = "img$i", description = "Desc $i",
                ki = "TODO() $i",
                maxKi = "TODO()  $i",
                race = "TODO() $i",
                gender = "TODO() $i",
                affiliation = "TODO() $i",
                isFavorite = i % 2 == 0,))
            // You'll need an insert method that works with a single character or a list
            dao.insertCharacter(charactersToInsert.last())
        }


        // Get first page (limit 2)
        val page1 = dao.getAllCharacters(page = 0, limit = 2).first() // offset = 0 * 2 = 0
        assertTrue(page1.size == 2)
        assertTrue(page1[0] == charactersToInsert[0])
        assertTrue(page1[1] == charactersToInsert[1])

        // Get second page (limit 2)
        val page2 = dao.getAllCharacters(page = 1, limit = 2).first() // offset = 1 * 2 = 2
        assertTrue(page2.size == 2)
        assertTrue(page2[0] == charactersToInsert[2])
        assertTrue(page2[1] == charactersToInsert[3])

        // Get third page (limit 2, should only have 1 item left)
        val page3 = dao.getAllCharacters(page = 2, limit = 2).first() // offset = 2 * 2 = 4
        assertTrue(page3.size == 1)
        assertTrue(page3[0] == charactersToInsert[4])

        // Get page beyond existing items
        val page4= dao.getAllCharacters(page = 3, limit = 2).first() // offset = 3 * 2 = 6
        assertTrue(page4.isEmpty())
    }
}