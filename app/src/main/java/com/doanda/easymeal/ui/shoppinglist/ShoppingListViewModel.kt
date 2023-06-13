package com.doanda.easymeal.ui.shoppinglist

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.ShoppingRepository
import com.doanda.easymeal.data.repository.UserRepository

class ShoppingListViewModel(
    private val userRepository: UserRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    fun getUser() = userRepository.getUser()

//    fun getShoppingList(userId: Int) = shoppingRepository.getShoppingList(userId)

//    fun addShoppingListItem(
//        userId: Int,
//        ingId: Int,
//        qty: Float,
//        unit: String,
//    ) = shoppingRepository.addShoppingListItem(userId, ingId, qty, unit)

    fun deleteShoppingListItem(
        userId: Int,
        ingId: Int,
    ) = shoppingRepository.deleteShoppingListItem(userId, ingId)

    fun getLoginStatus() = userRepository.getLoginStatus()
    fun getShoppingListLocal() = shoppingRepository.getShoppingListLocal()
}