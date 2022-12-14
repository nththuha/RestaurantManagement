package com.example.ordermanagement.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ordermanagement.model.*
import com.example.ordermanagement.network.RestaurantApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RestaurantViewModel : ViewModel() {
    lateinit var nhanVien: NhanVienEntity

    private val _dsPhieuDat = MutableLiveData<List<TicketReservationEntity>>()
    val dsPhieuDat: LiveData<List<TicketReservationEntity>> = _dsPhieuDat

    private val _phieuDat = MutableLiveData<TicketReservationEntity>()
    val phieuDat: LiveData<TicketReservationEntity> = _phieuDat

    private val _dsCTDatMon = MutableLiveData<List<OrderDetailEntity>>()
    val dsCTDatMon: LiveData<List<OrderDetailEntity>> = _dsCTDatMon

    private val _ctDatMon = MutableLiveData<OrderDetailEntity>()
    val ctDatMon: LiveData<OrderDetailEntity> = _ctDatMon

    private val _dsKhachHang = MutableLiveData<List<CustomerEntity>>()
    val dsKhachHang: LiveData<List<CustomerEntity>> = _dsKhachHang

    private val _khachHang = MutableLiveData<CustomerEntity>()
    val khachHang: LiveData<CustomerEntity> = _khachHang

    private val _dsPhong = MutableLiveData<List<RoomEntity>>()
    val dsPhong: LiveData<List<RoomEntity>> = _dsPhong

    private val _dsBan = MutableLiveData<List<TableEntity>>()
    val dsBan: LiveData<List<TableEntity>> = _dsBan

    private val _phong = MutableLiveData<RoomEntity>()
    val phong: LiveData<RoomEntity> = _phong

    private val _ban = MutableLiveData<TableEntity>()
    val ban: LiveData<TableEntity> = _ban

    private val _dsLMA = MutableLiveData<List<TypeOfFoodEntity>>()
    val dsLMA: LiveData<List<TypeOfFoodEntity>> = _dsLMA

    private val _dsMA = MutableLiveData<List<FoodEntity>>()
    val dsMA: LiveData<List<FoodEntity>> = _dsMA

    private val _loaiMonAn = MutableLiveData<TypeOfFoodEntity>()
    val loaiMonAn: LiveData<TypeOfFoodEntity> = _loaiMonAn

    private val _monAn = MutableLiveData<FoodEntity>()
    val monAn: LiveData<FoodEntity> = _monAn

    init {
        reset()
    }

    fun layDSDatMonTheoPD(): LiveData<List<OrderDetailEntity>> {
        viewModelScope.launch {
            try {
                _dsCTDatMon.value = phieuDat.value?.let {
                    RestaurantApi.retrofitService.layDSDatMonTheoPD(it.idPD)
                }
            } catch (e: Exception) {
                Log.e("L???i load ds CTDM", e.message.toString())
            }
        }
        return dsCTDatMon
    }

    fun layDSPhieuDatChuaCoHoaDon(): LiveData<List<TicketReservationEntity>> {
        viewModelScope.launch {
            try {
                _dsPhieuDat.value = RestaurantApi.retrofitService.layDSPhieuDatChuaCoHoaDon()
            } catch (e: Exception) {
                Log.e("L???i load ds phi???u ?????t", e.message.toString())
            }
        }
        return dsPhieuDat
    }

    fun chonPhieuDat(phieuDat: TicketReservationEntity) {
        _phieuDat.value = phieuDat
    }

    fun themPhieuDat(ticketReservationEntity: TicketReservationEntity): LiveData<TicketReservationEntity> {
        runBlocking {
            try {
                _phieuDat.value =
                    RestaurantApi.retrofitService.themPhieuDat(ticketReservationEntity)
            } catch (e: Exception) {
                Log.e("L???i th??m phi???u ?????t", e.message.toString())
            }
        }
        return phieuDat
    }

    fun themCT_DatMon(ctdatmon: OrderDetailEntity): MutableLiveData<OrderDetailEntity> {
        runBlocking {
            try {
                _ctDatMon.value = RestaurantApi.retrofitService.themCTDatMon(ctdatmon)
            } catch (e: Exception) {
                Log.e("L???i th??m ct ?????t m??n", e.message.toString())
            }
        }
        return _ctDatMon
    }

    fun suaCT_DatMon(ctdatmon: OrderDetailEntity): MutableLiveData<OrderDetailEntity> {
        viewModelScope.launch {
            try {
                _ctDatMon.value = RestaurantApi.retrofitService.suaCTDatMon(ctdatmon)
            } catch (e: Exception) {
                Log.e("L???i s???a ct ?????t m??n", e.message.toString())
            }
        }
        return _ctDatMon
    }

    fun xoaCT_DatMon(idCTDM: Int): Boolean {
        var s = true
        viewModelScope.launch {
            try {
                s = RestaurantApi.retrofitService.xoaCTDatMon(idCTDM)
            } catch (e: Exception) {
                Log.e("L???i x??a CT ?????t m??n", e.message.toString())
            }
        }
        return s
    }

    fun layDSKhachHang(): LiveData<List<CustomerEntity>> {
        viewModelScope.launch {
            try {
                _dsKhachHang.value = RestaurantApi.retrofitService.layDSKhachHang()
            } catch (e: Exception) {
                Log.e("L???i load ds kh??ch h??ng", e.message.toString())
            }
        }
        return dsKhachHang
    }

    fun chonKhachHang(customerEntity: CustomerEntity) {
        _khachHang.value = customerEntity
    }

    fun layDSPhong(): LiveData<List<RoomEntity>> {
        viewModelScope.launch {
            try {
                _dsPhong.value = RestaurantApi.retrofitService.getDSPhong()
            } catch (e: Exception) {
                Log.e("L???i load ds ph??ng", e.message.toString())
            }
        }
        return dsPhong
    }

    fun chonPhong(phong: RoomEntity) {
        this._phong.value = phong
    }

    fun layDSBanTheoPhong(): LiveData<List<TableEntity>> {
        viewModelScope.launch {
            try {
                _dsBan.value =
                    RestaurantApi.retrofitService.getDSBanTheoPhong(phong.value?.maPhong + "")
            } catch (e: Exception) {
                Log.e("L???i load ds b??n", e.message.toString())
            }
        }
        return dsBan
    }

    fun chonBan(ban: TableEntity) {
        this._ban.value = ban
    }

    fun layDSLoaiMonAn(): LiveData<List<TypeOfFoodEntity>> {
        viewModelScope.launch {
            try {
                _dsLMA.value = RestaurantApi.retrofitService.getDSLoaiMonAn()
            } catch (e: Exception) {
                Log.e("L???i load ds lo???i m??n ??n", e.message.toString())
            }
        }
        return dsLMA
    }

    fun chonLoaiMonAn(loaiMonAn: TypeOfFoodEntity) {
        this._loaiMonAn.value = loaiMonAn
    }

    fun chonMonAn(monAn: FoodEntity) {
        this._monAn.value = monAn
    }

    fun layDSMonAnTheoLoaiMonAn(): LiveData<List<FoodEntity>> {
        viewModelScope.launch {
            try {
                _dsMA.value =
                    RestaurantApi.retrofitService.getDSMonAnTheoLoaiMonan(loaiMonAn.value?.maLMA + "")
            } catch (e: Exception) {
                Log.e("L???i load ds m??n ??n", e.message.toString())
            }
        }
        return dsMA
    }

    fun themNhanVien(nhanVien: NhanVienEntity) {
        this.nhanVien = nhanVien
    }

    fun reset() {
        nhanVien = NhanVienEntity(1, "error", "error", "error", "error", "error", "error", "error")
    }
}