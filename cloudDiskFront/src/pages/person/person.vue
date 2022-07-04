<template>
  <div>
    <Header />

    <div class="person">
      <div class="person_left">
        <el-menu
          :default-active="active_index"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          :unique-opened="true"
        >
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>个人信息</span>
            </template>
            <el-menu-item-group>
              <div class="ment_item" @click="goInfo('personInfo')">
                <el-menu-item index="1-1" >基本信息</el-menu-item>
              </div>
              <el-menu-item index="1-2" @click="goInfo('changeInfo')">修改信息</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-folder-opened"></i>
              <span>文件管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1"  @click="goInfo('personFiles')">我的文件</el-menu-item>
              <el-menu-item index="2-2" @click="goInfo('collectFile')">收藏文件</el-menu-item>
              <el-menu-item index="2-3" @click="goInfo('shareFile')">共享文件</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="3" >
              <div class="ment_item" @click="goInfo('historyFile')">
                    <i class="el-icon-time"></i>
            <span slot="title" >浏览历史</span>
            </div>

          </el-menu-item>
          <el-menu-item index="4">
            <div class="ment_item" @click="goInfo('delFile')">
                    <i class="el-icon-delete"></i>
            <span slot="title">回收站</span>
            </div>
          </el-menu-item>

          <el-menu-item index="5">
            <div class="ment_item" @click="goBackPlaza">
              <i class="el-icon-s-home"></i>
              <span slot="title" >回到广场</span>
            </div>
          </el-menu-item>
        </el-menu>
      </div>
      <div class="person_right">
        <transition name="test" mode="out-in">
          <router-view />
        </transition>
      </div>
    </div>
  </div>
</template>

<style lang="less" scoped>
@import "./persons.less";
@import "../../public/public.less";
</style>

<script>
import Persons from './persons'
export default Persons
</script>
