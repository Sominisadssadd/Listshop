package com.example.listshop.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.listshop.domain.ShopItem
import com.example.listshop.domain.UseCaseEditShopItem
import com.example.listshop.domain.UseCaseGetShopItemList
import com.example.listshop.domain.UseCaseRemoveShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    application: Application,
    val getShopListUseCase: UseCaseGetShopItemList,
    val editShopItemUseCase: UseCaseEditShopItem,
    val removeShopItemUseCase: UseCaseRemoveShopItem

) : AndroidViewModel(application) {


    fun getShopList(): LiveData<List<ShopItem>> {
        return getShopListUseCase.getShopList()
    }


    fun changeEnabledState(shopItem: ShopItem) {
        //ViewModelScope - это то же самое что - CoroutineScope(Dispatcher.MAIN)
        //и еще он сам очишается в методе onCleared
        viewModelScope.launch {
            var oldElement = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(oldElement)
        }

    }

    fun removeShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }


}