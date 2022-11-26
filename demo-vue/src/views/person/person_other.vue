<template>
  <div>
    <el-container>
      <el-header width="100%">
        <el-card class="box-card1" shadow="hover">
          <el-container>
            <el-aside width="950px">
              <div class="top-card">
                <el-form :inline="true"> 委托 </el-form>
              </div>
            </el-aside>
            <el-container>
              <el-header>
                <el-input
                  :suffix-icon="Search"
                  placeholder="搜索委托"
                  size="large"
                  class="headerInput"
                />
              </el-header>
            </el-container>
          </el-container>
        </el-card>
      </el-header>
      <el-container>
        <el-aside width="1000px">
          <br />
          <div style="float: right">
            <br />

            <div v-if="isPoint === '2'">
              <el-button color="#000000" @click="pointFollow">关注</el-button>
            </div>
            <div v-if="isPoint === '1'">
              <el-button color="#000000" @click="pointFollow"
                >取消关注</el-button
              >
            </div>
          </div>

          <div>
            <el-avatar
              :size="200"
              :src="
                'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                ruleForm.avatar
              "
              class="avatar2"
            />

            <div class="name">{{ ruleForm.name }}</div>
            {{ ruleForm.username }}
            <br /><br />
            {{ ruleForm.signature }}
            <br />
            <el-button link type="" key="plain" style="font-size: 20px"
              >{{ follow.count}}关注</el-button
            >&nbsp;&nbsp;
            <el-button link type="" key="plain" style="font-size: 20px"
              >{{follow.fans}}粉丝</el-button
            >
          </div>
          <el-tabs v-model="activeName" class="aa" @tab-click="handleClick">
            <div class="aa">
              <el-tab-pane label="委托" name="first" @click="getCommission">
                <div
                  v-for="o in commissions.commission"
                  :key="o"
                  class="infinite-list-item"
                >
                  <el-card width="1000px">
                    <el-container>
                      <el-aside width="50px">
                        <!-- 头像 -->

                        <el-avatar
                          :size="50"
                          :src="
                            'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                            o.avatar
                          "
                          class="avatar"
                      /></el-aside>
                      <el-container>
                        <el-header height="10px">
                          <div>{{ o.name }}</div>
                          {{ o.date }}
                          <div v-if="o.status == '0'">
                            <el-button @click="postCommission(o.id)"
                              >--修改--</el-button
                            >
                            <el-button @click="deleteCommission(o.id)"
                              >--删除--</el-button
                            >
                          </div>
                          <div class="reception" v-if="o.status == '1'">
                            已被接
                          </div>
                          <div class="reception" v-if="o.status == '0'">
                            未被接
                          </div>
                        </el-header>
                      </el-container>
                    </el-container>
                    <br /><br />
                    <el-descriptions
                      direction="vertical"
                      :column="4"
                      size="large"
                      border
                    >
                      <el-descriptions-item label="学校">
                        <el-tag size="small">{{ o.school }}</el-tag>
                      </el-descriptions-item>
                      <el-descriptions-item label="地址">{{
                        o.commissionAddress
                      }}</el-descriptions-item>

                      <el-descriptions-item label="联系方式" :span="2">
                        {{ o.contact }}
                      </el-descriptions-item>

                      <el-descriptions-item label="委托" :span="4"
                        >{{ o.content }}
                      </el-descriptions-item>
                      <el-descriptions-item label="金额"
                        >{{ o.money }}
                      </el-descriptions-item>
                      <el-descriptions-item label="接单人"
                        >{{ o.acceptName }}
                      </el-descriptions-item>
                    </el-descriptions>
                  </el-card>
                  <br />
                </div>
              </el-tab-pane>
            </div>
            <button @click="getCommission">aaaaaa</button>
            <el-tab-pane label="动态" name="second" @click="getMoments">
              <div v-for="o in moments.moments" :key="o">
                <el-card
                  class="box-card"
                  shadow="hover"
                  @click="ViewInformation(o.id)"
                >
                  <el-container>
                    <el-aside width="50px">
                      <el-avatar
                        :size="50"
                        :src="
                          'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                          o.avatar
                        "
                      />
                    </el-aside>
                    <el-container>
                      <el-header height="10px">
                        <div class="name">{{ o.name }}--{{ o.school }}</div>
                        {{ o.date }}
                        <el-dropdown
                          :hide-on-click="false"
                          style="float: right"
                          v-if="o.student_id === selfStudentId"
                        >
                          <el-icon size="25px"><More /></el-icon>

                          <template #dropdown>
                            <el-dropdown-menu>
                              <el-dropdown-item @click="deleteMoments(o.id)"
                                >删除</el-dropdown-item
                              >
                              <el-dropdown-item @click="updateMoments(o.id)"
                                >修改</el-dropdown-item
                              >
                            </el-dropdown-menu>
                          </template>
                        </el-dropdown>
                      </el-header>
                      <el-main>
                        <br />
                        <br />
                        <div class="comment">
                          {{ o.content }}
                          <!-- <div v-for="i in o.photoName" :key="i" >
                        <img :src="'http://localhost:3000/src/images/'+i" class="image"   /></div> -->
                          <!-- 显示图片 -->
                          <div class="demo-image__preview">
                            <el-image
                              v-for="image in o.photoName"
                              :key="image"
                              style="width: 200px; height: 200px"
                              :src="
                                'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                                image
                              "
                              :preview-src-list="srcList"
                              :initial-index="1"
                              :headers="headerObj"
                              preview="1"
                              fit="cover"
                            />
                          </div>
                        </div>
                      </el-main>
                      <el-footer>
                        <!-- 如果点赞的动态id不和动态id相等（刚开始时like.momentsId为空，所以成立） -->
                        <div v-if="like.momentsId !== o.id">
                          <el-button
                            text
                            circle
                            @click.stop="beforeComment(o.id)"
                          >
                            <el-icon size="25px"><ChatRound /></el-icon>&nbsp;{{
                              o.count
                            }}
                          </el-button>

                          &nbsp;
                          <el-button text circle @click.stop="likePoint(o.id)">
                            <el-icon size="25px" color="red"
                              ><Pointer /></el-icon
                            >&nbsp;{{ o.likeCount }}
                          </el-button>
                        </div>

                        <!--点赞后查询点赞的动态id，所以此时like.momentsId === o.id（点赞后使用这个查询） -->
                        <div v-if="like.momentsId === o.id">
                          <el-button
                            text
                            circle
                            @click.stop="dialogTableVisible = true"
                          >
                            <el-icon size="25px"><ChatRound /></el-icon>&nbsp;{{
                              o.count
                            }}
                          </el-button>

                          &nbsp;
                          <el-button text circle @click.stop="likePoint(o.id)">
                            <el-icon size="25px" color="red"
                              ><Pointer /></el-icon
                            >&nbsp;{{ like.likeCount }}
                          </el-button>
                        </div>
                      </el-footer>
                    </el-container>
                  </el-container>
                </el-card>
              </div>
            </el-tab-pane>
            <el-tab-pane label="文章" name="third">Role</el-tab-pane>
            <el-tab-pane label="关注" name="fourth">Task</el-tab-pane>
          </el-tabs>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
  <!-- 外面评论对话框 -->
  <el-dialog v-model="dialogTableVisible2" title="评论">
    <el-input
      :rows="2"
      type="textarea"
      placeholder="评论"
      class="textarea"
      clearable
      v-model="comment"
    />

    <br />
    <br />
    <el-row justify="end">
      <el-button type="primary" round @click="saveComment(selectMomentstId)"
        >评论</el-button
      >
    </el-row>
  </el-dialog>
</template>
      <script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { onMounted, reactive, ref } from "vue";
import {
ElAside, ElAvatar, ElButton, ElCard, ElCheckbox, ElCheckboxGroup, ElCol, ElContainer, ElDatePicker, ElDescriptions, ElDescriptionsItem, ElDialog, ElForm, ElFormItem, ElHeader, ElInput, ElMain, ElOption, ElRadio, ElRadioGroup, ElSelect, ElSelectV2, ElSwitch, ElTabPane, ElTabs, ElTag,   Action,
  ElMessage,
  ElMessageBox,
  FormInstance,
  FormRules,
  TabsPaneContext,
  UploadProps,
} from "element-plus";
import axios from "axios";
import { InfoFilled } from "@element-plus/icons-vue";
import router from "../../router"
import { useRoute } from "vue-router";
const activeName = ref("first");
const dialogTableVisible = ref(false);
const dialogTableVisible2 = ref(false);
const ruleFormRef = ref<FormInstance>();

const headerObj = ref({ token: sessionStorage.getItem("token") });
const selectMomentstId = ref("");

const route = useRoute();
//查看用户信息的id
const nowStudentId = route.query.toStudentId;
//自己的id
const selfStudentId = sessionStorage.getItem("studentId");
//个人质料表单
const ruleForm = reactive({
  name: "Hello",
  school: "",
  birthday: "",
  sex: "",
  enrollmentTime: "",
  email: "",
  phone: "",
  address: "",
  school_address: "",
  avatar: "",
  signature: "",
  age: "",
  username: "",
});

//点赞
const like = ref({
  //点赞数量
  likeCount: "",
  //是否点赞 1点赞 0未点赞
  isLike: "",
  //点赞的动态id
  momentsId: "",
  //标记变量
  temp: "",
});
const commissions = reactive({
  commission: [],
});
const moments = reactive({
  moments: [],
});
const centerDialogVisible = ref(false);
onMounted(() => {
  isPointStudent();
  //查看关注数量
  getFollowCount();
  getFansCount();
  //获取个人信息
  getPerson();
  //获取委托
  getCommission();
  //获取动态
  getMoments();
});
//-------------------------------------动态---------动态------------动态------动态------------------------

//查询详细的动态信息
const ViewInformation = (id) => {
  router.push({
    path: "information",
    query: { momentsId: id },
  });
};
//查询点赞个数
const getLikeCount = (momentsId) => {
  axios
    .post(
      "/demo/like/getLikeCount",
      { momentsId, nowStudentId },
      {
        headers: { token: sessionStorage.getItem("token") },
      }
    )
    .then((res) => {
      like.value.likeCount = res.data.result;
    })
    .catch((error) => {
      ElMessage.error("查询点赞失败");
    });
};
//点赞
const likePoint = (momentsId) => {
  axios
    .post(
      "/demo/like/doLike",
      { momentsId: momentsId, studentId: nowStudentId },
      {
        headers: { token: sessionStorage.getItem("token") },
      }
    )
    .then((res) => {
      like.value.isLike = res.data.result.result;
      like.value.momentsId = res.data.result.momentsId;
      like.value.temp = "1";
      getLikeCount(momentsId);
    })
    .catch((error) => {
      ElMessage.error("点赞失败");
    });
};
//根据学生id查询动态
const getMoments = () => {
  axios
    .get(
      "/demo/moments/getMomentsAndStudentByStuId?studentId=" + nowStudentId,
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      moments.moments = res.data.result;
    })
    .catch((error) => {
      ElMessage.error("查看动态");
    });
};
//点击评论按钮
const beforeComment = (id) => {
  dialogTableVisible2.value = true;
  selectMomentstId.value = id;
};
//评论
const comment = ref("");
const saveComment = (momentsId) => {
  axios
    .post(
      "/demo/comment/saveComment",
      {
        comment: comment.value,
        studentId: sessionStorage.getItem("studentId"),
        studentName: sessionStorage.getItem("studentName"),
        momentsId: momentsId,
      },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      comment.value = "";
      if (res.data.result === false) {
        ElMessage.error("评论失败");
      }

      dialogTableVisible2.value = false;
      getMoments();
    })
    .catch((error) => {
      ElMessage.error("评论失败");
    });
};
//关注****----------------------关注--------------------关注-------------------关注-------------
const follow = reactive({
  count: "",
  fans:"",
});
//根据用户id查看粉丝的数量
const getFansCount = () => {
  axios
    .post("/demo/follow/getFansCount", nowStudentId, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {
      follow.fans = res.data.fans;
    })
    .catch((error) => {
      ElMessage.error("查看粉丝数量失败");
    });
};
//根据用户id查看关注的数量
const getFollowCount = () => {
  axios
    .post("/demo/follow/getFollowCount", nowStudentId, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {
      follow.count = res.data.result;
    })
    .catch((error) => {
      ElMessage.error("查看关注数量失败");
    });
};
//-----个人资料--个人资料-------个人资料-----------个人资料----------------------个人资料-----------个人资料
//查看是否已关注此用户
const isPoint = ref("");

const isPointStudent = () => {
  axios
    .post(
      "/demo/follow/isPointStudent",
      {
        selfStudentId: selfStudentId,
        followStudentId: nowStudentId,
      },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      isPoint.value = res.data.result;
      console.log("isPoint.value", isPoint.value);
    })
    .catch((error) => {
      ElMessage.error("查看是否关注失败");
    });
};
//点关注
const sonPointFollow = () => {
  axios
    .put(
      "/demo/follow/PointFollow",
      {
        selfStudentId: selfStudentId,
        followStudentId: nowStudentId,
      },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      isPointStudent();
    })
    .catch((error) => {
      ElMessage.error("点击关注失败");
    });
};

//点关注的具体实现
const pointFollow = () => {
  if (isPoint.value === "2") {
    sonPointFollow();
  }
  if (isPoint.value === "1") {
    ElMessageBox.confirm(
      "是否取消关注",

      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }
    )
      .then(() => {
        sonPointFollow();
      })
      .catch(() => {});
  }
};
//获取个人信息
const getPerson = () => {
  axios
    .get("/demo/student/getStudentById?studentId=" + nowStudentId, {
      headers: {
        token: sessionStorage.getItem("token"),
      },
    })
    .then((res) => {
      ruleForm.name = res.data.result.name;
      ruleForm.sex = res.data.result.sex;
      ruleForm.avatar = res.data.result.avatar;
      ruleForm.school_address = res.data.result.school_address;
      ruleForm.address = res.data.result.address;
      ruleForm.phone = res.data.result.phone;
      ruleForm.email = res.data.result.email;
      ruleForm.enrollmentTime = res.data.result.enrollmentTime;
      ruleForm.school = res.data.result.school;
      ruleForm.birthday = res.data.result.birthday;
      ruleForm.signature = res.data.result.signature;
      ruleForm.age = res.data.result.age;
      ruleForm.username = res.data.result.username;
    })
    .catch((error) => {
      ElMessage.error("查看评论失败");
    });
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
  dialogTableVisible.value = false;
};

const options = Array.from({ length: 150 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}));
//上传头像(修改头像)
const uploadAvatar = () => {};

//-----------------------委托-------委托*-------------------------委托*******************----------------委托
//查询接单人信息
const getAcceptStudentByAcceptStudentId = (id) => {
  axios
    .post(
      "/demo/commission/getAcceptStudentByAcceptStudentId",
      { acceptStudentId: id },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      commissions.commission = res.data.result;
      console.log("commissions.commission", commissions.commission);
    })
    .catch((error) => {
      ElMessage.error("查询委托失败");
    });
};
const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event);
};

//编辑资料
const updatePerson = () => {
  dialogTableVisible.value = true;
};

//查询自己的委托
const getCommission = () => {
  axios
    .post(
      "/demo/commission/getCommissionAndStudentByStudentId",

      nowStudentId,
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      commissions.commission = res.data.result;
    })
    .catch((error) => {
      ElMessage.error("查询委托失败");
    });
};
const cancelEvent = () => {
  console.log("cancel!");
};
const imageUrl = ref("");
</script>
      <style>
.top-card {
  font-weight: bold;
  font-size: x-large;
}
.headerInput {
  width: 400px;
  border-right: 0ch;
}
.box-card1 {
  width: 84%;
  height: 70px;
  position: fixed;
  left: 250px;
  right: 0;
  z-index: 5;
}
.box-card {
  width: 99%;
  cursor: pointer;
}
.page-box {
  background-color: #ffffff;
  height: 100%;
}

.avatar2 {
  text-align: center;
}
.avatar {
  text-align: center;
}
.demo-tabs > .el-tabs__content {
  padding: 100px;
  color: #6b778c;
  font-size: 100px;
  font-weight: 100;
  text-align: center;
  font-size: xx-large;
}

.reception {
  text-align: right;
  color: crimson;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius: 89px;
}
</style>
  <style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 89px;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  border-radius: 89px;
  text-align: center;
}
</style>