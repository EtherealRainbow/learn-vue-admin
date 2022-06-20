<template>
  <div class="filleSystem">
    <div class="nav">
      <Breadcrumb>
        <BreadcrumbItem
          ><span @click="goSystem">系统文件</span>
        </BreadcrumbItem>
        <BreadcrumbItem v-for="(item, index) in navList" :key="index">
          <span @click="goFile(item, index)">{{ item.systemName }}</span>
        </BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="list">
      <div class="listLeft">
        <div class="search">
          <div class="searchItem">
            <span>文件名称:</span>
            <Input placeholder="请输入" v-model="name" style="width: 200px" />
          </div>
          <!-- <div class="searchItem">
            <span>文件类型:</span>
            <Select v-model="model1" style="width: 200px">
              <Option
                v-for="item in cityList"
                :value="item.value"
                :key="item.value"
                >{{ item.label }}</Option
              >
            </Select>
          </div> -->
          <Button type="info" @click="searchInfo" style="margin-right:20px;">查询</Button>
           <Button  @click="rest">重置</Button>
        </div>
        <div class="fileList" v-if="system" @click="closeAll">
          <div
            @click.stop="seeDetails(item, index, 0)"
            class="fileItem"
            :class="{ choosed: active == index }"
            v-for="(item, index) in list"
            :key="index"
          >
            <div class="item" @dblclick="intoFile(item)">
              <img src="../../assets/wjj.png" alt="" />

              <div>{{ item.systemName }}</div>
            </div>
          </div>
        </div>
        <div
          class="fileList"
          @contextmenu.prevent="showBigMenu"
          @click="closeAll"
          v-else
        >
          <div
            class="fileItem"
            :class="{ choosed: childActive == index }"
            v-for="(item, index) in list"
            :key="index"
            @contextmenu.prevent.stop="showMenu(item, index)"
            @click.stop="seeDetails(item, index, 1)"
          >
            <div class="item" @dblclick="intoFile(item)">
              <img
                src="../../assets/wjj.png"
                v-if="item.fileType == 'WJJ'"
                alt=""
              />
              <img
                src="../../assets/txt.png"
                v-else-if="item.suffix == 'txt'"
                alt=""
              />
              <img
                src="../../assets/WORD.png"
                v-else-if="item.suffix == 'doc' || item.suffix == 'docx'"
                alt=""
              />
              <img
                src="../../assets/img.png"
                v-else-if="item.suffix == 'png' || item.suffix == 'jpg'"
                alt=""
              />
              <img
                src="../../assets/pdf.png"
                v-else-if="item.suffix == 'ppt' || item.suffix == 'pptx'"
                alt=""
              />
              <img
                src="../../assets/excel.png"
                v-else-if="item.suffix == 'xls' || item.suffix == 'xlsx'"
                alt=""
              />
              <img v-else src="../../assets/wj.png" alt="" />
              <Input
                autofocus
                v-model="item.systemName"
                v-if="item.inputFlag"
                @on-blur="blur(item)"
              />
              <div v-else>{{ item.systemName }}</div>
            </div>
            <div class="menu" v-show="item.showFlag">
              <div
                class="menuItem"
                @click="downFile(item)"
                v-if="item.fileType != 'WJJ'"
              >
                下载
              </div>
              <div class="menuItem" @click="deleteData(item)">删除</div>
              <div class="menuItem" @click="setName(item)">重命名</div>
            </div>
          </div>
        </div>
      </div>
      <div class="listRight" v-if="!system">
        <div v-if="childrendetilsList.systemName">
          <div class="rightItem">
            <span>上传者:</span>
            <div>{{ childrendetilsList.createUser }}</div>
          </div>
          <div class="rightItem">
            <span>文件大小:</span>
            <div>{{ childrendetilsList.fileSize }}</div>
          </div>
          <div class="rightItem">
            <span>文件类型:</span>
            <div>{{ childrendetilsList.fileType }}</div>
          </div>
          <div class="rightItem">
            <span>创建时间:</span>
            <div>{{ childrendetilsList.createDate }}</div>
          </div>
          <div class="rightItem">
            <span>位置:</span>
            <div>{{ childrendetilsList.path }}</div>
          </div>
          <div class="rightItem">
            <span>上次修改时间:</span>
            <div>{{ childrendetilsList.updateDate }}</div>
          </div>
          <!-- <div class="rightItem">
            <span>上次打开时间:</span>
            <div>{{ childrendetilsList.userName }}</div>
          </div> -->
        </div>
      </div>
      <div class="listRight" v-else>
        <div v-if="detilsList.systemName">
          <div class="rightItem">
            <span>文件类型:</span>
            <div>{{ detilsList.type }}</div>
          </div>

          <div class="rightItem">
            <span>创建人:</span>
            <div>{{ detilsList.createUser }}</div>
          </div>
          <div class="rightItem">
            <span>创建时间:</span>
            <div>{{ detilsList.createDate }}</div>
          </div>
          <div class="rightItem">
            <span>位置:</span>
            <div>{{ detilsList.path }}</div>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="!system"
      class="menu"
      v-show="showList"
      style="position: fixed"
      :style="{ top: this.y + 'px', left: this.x + 'px' }"
    >
      <!-- <div class="menuItem">查看</div>
      <div class="menuItem">修改</div> -->
      <div class="menuItem">
        <Upload
          :action="baseUrl + 'ftpFileInfo/uploadFiles'"
          :data="fileData"
          :show-upload-list="false"
          :on-success="handleSuccess"
        >
          <div style="width: 100px" @click="showList=false">上传</div>
        </Upload>
      </div>
      <div class="menuItem" @click="newFile">新建文件夹</div>
    </div>
    <Modal v-model="modal1" title="新建">
      <Form ref="formInline" :model="formInline" :rules="ruleInline">
        <FormItem prop="user" label="文件名" :label-width="80">
          <Input
            type="text"
            v-model="formInline.user"
            placeholder="请输入"
            style="width: 200px"
          >
          </Input>
        </FormItem>
        <!-- <FormItem label="类型" :label-width="80">
          <Select v-model="formInline.type" style="width: 200px" >
            <Option
              v-for="item in cityList"
              :value="item.value"
              :key="item.value"
              >{{ item.label }}</Option
            >
          </Select>
        </FormItem> -->
        <!-- <FormItem>
          <Button type="primary" @click="handleSubmit('formInline')"
            >添加</Button
          >
        </FormItem> -->
      </Form>
      <div slot="footer" class="foot">
        <Button type="primary" @click="handleSubmit('formInline')">添加</Button>
        <Button @click="modalClose">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import {
  getFtpConnectionInfoList,
  getFTPChildren,
  addFtpFileInfo,
  updateFtpFileInfo,
  deleteFtpFileInfo,
} from "../../api/ftp";
import config from "@/config";
export default {
  data() {
    return {
      baseUrl:
        process.env.NODE_ENV === "development"
          ? config.baseUrl.dev
          : config.baseUrl.pro,
      detilsList: {}, //文件详情
      childrendetilsList: {}, //子文件详情
      parentMsg: "", //当前父级信息
      system: true, //面包屑
      navList: [],
      formInline: {
        user: "",
      },
      ruleInline: {
        user: [
          {
            required: true,
            message: "请输入名称",
            trigger: "blur",
          },
        ],
      },
      active: -1,
      childActive: -1,
      modal1: false,
      showList: false,
      list: [],
      name: "",
      cityList: [],
      model1: "",
      x: 0,
      y: 0,
      fileData: {}, //上传文件
    };
  },
  methods: {
    //下载文件
    downFile(row) {
      window.location.href =
        this.baseUrl +
        "ftpFileInfo/downloadFile" +
        "?rootUuid=" +
        this.navList[0].uuid +
        "&uuid=" +
        row.uuid;
      this.closeAll();
    },
    //上传成功
    handleSuccess(res, file) {
      console.log(res);
      this.getInfo(this.parentMsg);
      this.showList = false;
    },
    goSystem() {
      this.navList = [];
      this.getSystem();
      this.system = true;
    },
    goFile(item, index) {
      this.navList = this.navList.slice(0, index + 1);
      this.getInfo(item);
      this.system = false;
    },
    searchInfo() {
      if (this.system) {
        this.getSystem();
      } else {
        this.getInfo(this.parentMsg);
      }
    },
    rest(){
      if (this.system) {
        this.name=''
        this.getSystem();
      } else {
        this.name=''
        this.getInfo(this.parentMsg);
      }
    },
    modalClose() {
      this.modal1 = false;
      this.formInline.user = "";
    },
    //查看文件详情
    seeDetails(row, index, state) {
      this.showList=false
      if (state) {
        this.childrendetilsList = row;
        this.childActive = index;
        for (let i in this.list) {
          this.list[i].showFlag = false;
        }
        this.system = false;
      } else {
        this.detilsList = row;
        this.active = index;
      }
    },
    //进入ftp文件夹
    intoFile(row) {
      // console.log(row)
      if ((row.fileType == "WJJ" || row.type == "ftp") && !row.inputFlag) {
        this.active = -1;
        this.childActive = -1;
        this.detilsList = {};
        this.navList.push(row);
        this.getInfo(row);
      } else {
		  // window.open("ftp://192.168.1.201/FTP/1221111/QQ图片20210825150802.png");
        window.open(
          this.baseUrl +
            "ftpFileInfo/preview" +
            "?rootUuid=" +
            this.navList[0].uuid +
            "&uuid=" +
            row.uuid,
          "_blank"
        );
      }
    },
    getInfo(row) {
      let paraList = {
        systemId: row.uuid,
        start: 0,
        limit: 9999,
        systemName: this.name,
      };
      getFTPChildren(paraList).then((res) => {
        if (res.status === 200) {
          this.name = "";
          this.system = false;
          // console.log(this.system);
          this.detilsList = {};
          this.list = res.data.records;
          this.parentMsg = row;
        }
      });
    },
    handleSubmit(name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          // this.$Message.success("Success!");
          let path = "";
          for (let i in this.navList) {
            path += this.navList[i].systemName + "/";
          }
          let paraList = {
            rootUuid: this.navList[0].uuid,
            systemName: this.formInline.user,
            path: path,
            parentId: this.parentMsg.uuid,
            fileType: "WJJ",
          };
          addFtpFileInfo(paraList).then((res) => {
            if (res.status === 200) {
              this.formInline.user = "";
              this.system = false;
              this.modal1 = false;
              this.getInfo(this.parentMsg);
            }
          });
        } else {
          // this.$Message.error("Fail!");
        }
      });
    },
    //显示文件右键菜单
    showMenu(row, index) {
      this.showList = false;
      this.childActive = index;
      this.choose(row, "showFlag");
    },
    choose(row, type) {
      for (let i in this.list) {
        if (this.list[i].uuid === row.uuid) {
          this.list[i][type] = true;
        } else {
          this.list[i][type] = false;
        }
      }
    },
    //关闭右键菜单
    closeAll() {
      this.childrendetilsList = {};
      this.active = -1;
      this.childActive = -1;
      this.detilsList = {};
      this.showList = false;
      for (let i in this.list) {
        this.list[i].showFlag = false;
        // this.list[i].inputFlag = false;
      }
    },
    //显示列表右键菜单
    showBigMenu(event) {
      // this.closeAll();
      this.x = event.clientX;
      this.y = event.clientY;
      // console.log(event,this.x, this.y);
      this.showList = true;
      let ftpFileInfo = this.parentMsg;
      // console.log(this.parentMsg)
      let uuid = this.navList[0].uuid;
      let path = "";
      for (let i in this.navList) {
        path += this.navList[i].systemName + "/";
      }
      this.fileData = {
        rootUuid: uuid,
        path: path,
        uuid: ftpFileInfo.uuid,
      };
      // console.log(this.fileData);
    },
    newFile() {
      this.showList = false;
      this.modal1 = true;
    },
    setName(row) {
      this.choose(row, "inputFlag");
      for (let i in this.list) {
        this.list[i].showFlag = false;
      }
    },
    //修改文件名
    blur(item) {
      if (item.systemName.length > 0) {
        let path = "";
        for (let i in this.navList) {
          path += this.navList[i].systemName + "/";
        }
        for (let i in this.list) {
          if (this.list[i].uuid == item.uuid) {
            this.list[i].systemName = item.systemName;
            let paraList = {
              systemName: item.systemName,
              uuid: item.uuid,
              rootUuid: this.navList[0].uuid,
              path: path,
            };
            updateFtpFileInfo(paraList).then((res) => {
              if (res.status === 200) {
                this.getInfo(this.parentMsg);
              }
            });
          }
          this.list[i].inputFlag = false;
        }
        this.childActive = -1;
      } else {
        this.$Message.warning("名称不能为空");
      }
    },
    //删除
    deleteData(item) {
      this.closeAll();
      this.$Modal.confirm({
        title: "删除",
        content: "是否删除？",
        onOk: async () => {
          let path = "";
          for (let i in this.navList) {
            path += this.navList[i].systemName + "/";
          }
          let paraList = {
            uuid: item.uuid,
            path: path,
            rootUuid: this.navList[0].uuid,
          };
          deleteFtpFileInfo(paraList).then((res) => {
            if (res.status === 200) {
              this.getInfo(this.parentMsg);
              for (let i in this.list) {
                this.list[i].inputFlag = false;
              }
              this.childActive = -1;
            }
          });
        },
      });
    },
    getSystem() {
      let paraList = {
        start: 0,
        limit: 9999,
        systemName: this.name,
      };
      getFtpConnectionInfoList(paraList).then((res) => {
        // console.log(res);
        if (res.status == 200) {
          this.name = "";
          this.list = res.data.records;
        }
      });
    },
  },

  created() {
    this.getSystem();
  },
};
</script>

<style scoped>
.foot {
  text-align: center;
}
.filleSystem {
  width: 100%;
  height: 100%;
}
.nav {
  height: 20px;
  line-height: 20px;
  margin-bottom: 10px;
}
.nav span:hover {
  cursor: pointer;
  color: #2d8cf0;
}

.list {
  width: 100%;
  height: calc(100% - 20px);
  background: #fff;
  display: flex;
}
.listLeft {
  width: 70%;
  height: 100%;
  overflow-y: auto;
  box-sizing: border-box;
  padding: 10px;
}
.listRight {
  width: calc(30% - 10px);
  height: 100%;
  overflow-y: auto;
  border-left: 1px solid #eeeeeeb7;
  box-sizing: border-box;
  padding: 10px;
}
.rightItem {
  color: #333;
  font-size: 16px;
  margin-bottom: 10px;
  text-align: left;
  display: flex;
}
.rightItem span {
  width: 120px;
  text-align: right;
  font-weight: bold;
  margin-right: 10px;
}
.search {
  display: flex;
  margin-bottom: 40px;
}
.searchItem {
  margin-right: 20px;
}
.fileList {
  display: flex;
  width: 100%;
  align-content: flex-start;
  /* height: calc(100% - 40px); */
  min-height: calc(100% - 80px);
  /* overflow-y: auto; */
  box-sizing: border-box;
  padding: 10px;
  flex-wrap: wrap;
}
.fileItem {
  position: relative;
  width: calc(20% - 50px);
  margin-right: 50px;
  font-size: 16px;
  text-align: center;
  cursor: pointer;
  /* height: 120px; */
  min-height: 150px;
  margin-bottom: 20px;
}
.fileItem img {
  width: 50%;
}
.fileItem .item {
  overflow: hidden;
  width: 100%;
  position: relative;
}
.fileItem .item input {
  position: absolute;
  bottom: 0;
  left: 0;
  z-index: 100;
}
.menu {
  cursor: pointer;
  padding: 20px 0px;
  box-sizing: border-box;
  position: absolute;
  top: 50px;
  right: -50px;
  width: 100px;
  border: 1px solid #ddd;
  background: #efefef;
  font-size: 16px;
}
.menuItem {
  width: 100%;
  box-sizing: border-box;
  padding: 5px;
  line-height: 20px;
  border-bottom: 1px solid #ddd;
}
.menuItem:hover {
  background: gray;
}
.menuItem:first-child {
  border-top: 1px solid #ddd;
}
.choosed {
  border: 4px solid #2d8cf0;
  background: #2d8cf0;
  opacity: 0.7;
}
</style>
