<template>
  <div class="files">
    <div class="folder">
      <el-table
        ref="filterTable"
        :data="folderLists"
        style="width: 100%"
        class="box-card"
        height="85vh"
      >
        <el-table-column align="center" prop="fileName" label="名称" sortable>
          <template slot-scope="scope">
            <div
              class="file_folder_name"
              @click="handleEdit(scope.$index, scope.row)"
            >{{ scope.row.fileName }}</div>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="fileOthers" label="备注"></el-table-column>
        <el-table-column align="center" prop="fileUploadTime" label="日期" sortable></el-table-column>
        <el-table-column align="center">
        <template slot="header" slot-scope="scope">
            <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
          </template>
          <template slot-scope="scope">
            <el-popconfirm title="确定删除" @confirm="delFileFolder(scope.$index, scope.row)">
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                slot="reference"
              >取消分享</el-button>
            </el-popconfirm>
          </template>

        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<style lang="less">
    @import "./shareFiles.less";
</style>
<script>
  import ShareFiles from './shareFiles'
  export default ShareFiles;
</script>
