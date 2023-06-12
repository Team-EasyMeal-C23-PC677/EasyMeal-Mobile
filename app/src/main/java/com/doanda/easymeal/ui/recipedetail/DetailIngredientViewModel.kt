package com.doanda.easymeal.ui.recipedetail

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.ShoppingRepository

class DetailIngredientViewModel (
    private val ingredientRepository: IngredientRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    fun getIngredientsByIds(listId: List<Int>) =
        ingredientRepository.getIngredientsByIds(listId)

    fun getShoppingListByIds(listId: List<Int>) =
        shoppingRepository.getShoppingListByIds(listId)

    fun addPantryIngredient(userId: Int, ingId: Int) =
        ingredientRepository.addPantryIngredient(userId, ingId)

    fun deletePantryIngredient(userId: Int, ingId: Int) =
        ingredientRepository.deletePantryIngredient(userId, ingId)

    fun addShoppingListItem(userId: Int, ingId: Int, qty: Float, unit: String) =
        shoppingRepository.addShoppingListItem(userId, ingId, qty, unit)

    fun deleteShoppingListItem(userId: Int, ingId: Int) =
        shoppingRepository.deleteShoppingListItem(userId, ingId)
}
