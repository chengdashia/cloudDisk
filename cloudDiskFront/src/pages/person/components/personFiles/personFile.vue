<template>
  <div class="files">
    <!-- 个人文件信息页 -->
    <div class="file_top">
      <el-button type="primary" round @click="uploadtest">
        上传
        <i class="el-icon-upload el-icon--right"></i>
      </el-button>
      <el-button type="primary" round @click="dialogVisible = true">
        新增文件夹
        <i class="el-icon-folder-add"></i>
      </el-button>
      <el-dialog title="新增文件夹" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
        <div class="add_input">
          <el-input v-model="input" placeholder="文件夹名称"></el-input>
          <el-input v-model="fileTips" placeholder="文件夹备注"></el-input>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addFoler">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog title="上传文件" :visible.sync="fileVisible" width="30%" :before-close="handleClose">
        <div class="add_file">
          <div class="upload">
            <el-upload class="upload-demo" drag action="http://localhost:9081/fileInfo/uploadFile"
              :on-change="progress" :on-error="uploadError" :auto-upload="false" :on-remove="handleRemove" :limit="1"
              ref="upload" :headers="headers" :data="uploadData" :on-success="successUpload">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">
                将文件拖到此处，或
                <em>点击上传</em>
              </div>
            </el-upload>
          </div>
        </div>
        <div class="add_input">
          <el-input v-model="uploadData.fileName" placeholder="文件名称" disabled></el-input>
          <el-input v-model="uploadData.remarks" placeholder="文件备注"></el-input>
        </div>
        <div class="select_label">
          <el-select v-model="value" multiple filterable allow-create default-first-option placeholder="请选择文件标签"
            @change="selectLable">
            <el-option v-for="item in options" :key="item.interestLabelId" :label="item.labelName"
              :value="item.interestLabelId">
            </el-option>
          </el-select>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="fileVisible = false">取 消</el-button>
          <el-button type="primary" @click="addFiler">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <el-dialog title="修改文件名" :visible.sync="show_file_name" width="30%" :before-close="handleClose">
      <el-input :placeholder="file_info_name" v-model="fileInfo.name">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_file_name = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(1)">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="修改文件备注" :visible.sync="show_file_remark" width="30%" :before-close="handleClose">
      <el-input :placeholder="file_info_remark" v-model="fileInfo.remark">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_file_remark = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(2)">确 定</el-button>
      </span>
    </el-dialog>
   <el-dialog title="修改文件夹名字" :visible.sync="show_folder_name" width="30%" :before-close="handleClose">
      <el-input :placeholder="folder_info_name" v-model="folderInfo.name">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_folder_name = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(3)">确 定</el-button>
      </span>
    </el-dialog>
     <el-dialog title="修改文件夹备注" :visible.sync="show_folder_remark" width="30%" :before-close="handleClose">
      <el-input :placeholder="folder_info_remark" v-model="folderInfo.remark">
      </el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show_folder_remark = false">取 消</el-button>
        <el-button type="primary" @click="confirmChange(4)">确 定</el-button>
      </span>
    </el-dialog>
    <div class="directory_list">
      <div v-for="(item, index) in directoryList" :key="item.folderId" class="directory_name">
        <div @click="goFolderList(item, index)">{{ item.folderName }}
          <i class="el-icon-arrow-right" style="font-size:15px" v-if="index != directoryList.length - 1"></i>
        </div>
      </div>
    </div>
    <div class="folder">
      <el-table ref="filterTable" :data="folderLists" style="width: 100%" class="box-card" height="70vh"
        v-loading="loading">
        <!-- <el-table-column align="center" prop="folderFileName" label="名称"  sortable></el-table-column> -->
        <el-table-column align="center" prop="folderFileName" label="名称" sortable>
          <template slot-scope="scope">
            <div class="file_folder_name" @click="handleEdit(scope.row.type, scope.row.folderFileId)"
              v-if="scope.row.type == 2">
              {{ scope.row.folderFileName }}
            </div>
            <div class="file_folder_name" @click="goFolder(scope.row, scope.row.folderFileId)"
              v-if="scope.row.type == 1">
              {{ scope.row.folderFileName }}
            </div>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="folderFileTips" label="备注"></el-table-column>
        <el-table-column align="center" prop="folderFileTime" label="日期" sortable></el-table-column>
        <el-table-column align="center">
          <template slot="header" slot-scope="scope">
            <el-input v-model="search" size="mini" placeholder="输入关键字搜索" />
          </template>
          <template slot-scope="scope">
            <el-select v-model="scope.row.folderFileStatus" placeholder="" size="mini" v-if="scope.row.type == 2"
              @change="changeStatus($event, scope.row)">
              <el-option v-for="item in scope.row.options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>

            <!-- <el-button size="mini" @click="handleEdit2(scope.$index, scope.row)">编辑</el-button> -->
            <el-dropdown split-button type="warning" size="small" v-if="scope.row.type == 1">
              编辑
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="showFolderNameChange(scope.$index, scope.row)">更改文件夹名称</el-dropdown-item>
                <el-dropdown-item @click.native="showFolderRemarkChange(scope.$index, scope.row)">更改文件夹备注</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown split-button type="warning" size="small" v-if="scope.row.type == 2">
              编辑
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="showFileNameChange(scope.$index, scope.row)">更改文件名称</el-dropdown-item>
                <el-dropdown-item @click.native="showFileRemarkChange(scope.$index, scope.row)">更改文件备注</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-popconfirm title="确定删除" @confirm="delFileFolder(scope.$index, scope.row)">
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)" slot="reference">删除
              </el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="tag" label="类型" width="100" :filters="[
          { text: '文件', value: '2' },
          { text: '文件夹', value: '1' },
        ]" :filter-method="filterTag" filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag type="primary" disable-transitions v-if="scope.row.type == 1">文件夹</el-tag>
            <el-tag type="success" disable-transitions v-if="scope.row.type == 2">文件</el-tag>
          </template>
        </el-table-column>
        <el-backtop target=".box-card" :visibility-height="200"></el-backtop>
      </el-table>
    </div>
  </div>
</template>
<style lang="less" scoped>
@import "./personFiles.less";
</style>
<script >
import PersonFiles from "./personFiles";
export default PersonFiles;
</script>
