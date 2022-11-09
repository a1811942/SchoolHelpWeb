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
            <el-button type="info" @click="updatePerson">编辑资料</el-button>
          </div>
          <br />
          <br />
          <div style="text-align: center">
            <el-upload
              class="avatar-uploader"
              action=" http://localhost:8080/demo/UpdateAndDown/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :headers="headerObj"
              :before-upload="beforeAvatarUpload"
            >
              <el-avatar
                :size="200"
                :src="
                  'http://localhost:8080/demo/UpdateAndDown/down?name=' +
                  ruleForm.avatar
                "
                class="avatar2"
              />
            </el-upload>
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
            <el-tab-pane label="动态" name="second">Config</el-tab-pane>
            <el-tab-pane label="文章" name="third">Role</el-tab-pane>
            <el-tab-pane label="关注" name="fourth">Task</el-tab-pane>
          </el-tabs>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
  <el-dialog v-model="dialogTableVisible" title="编辑资料">
    <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      class="demo-ruleForm"
      :size="formSize"
      status-icon
    >
      <el-form-item label="昵称" prop="name">
        <el-input style="with: 50%" class="w-50 m-2" v-model="ruleForm.name" />
      </el-form-item>
      <el-form-item label="电话号码" prop="phone">
        <el-input style="with: 50%" class="w-50 m-2" v-model="ruleForm.phone" />
      </el-form-item>

      <el-form-item label="学生公寓" prop="address">
        <el-input
          style="with: 50%"
          class="w-50 m-2"
          v-model="ruleForm.address"
        />
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-select-v2
          v-model="ruleForm.age"
          placeholder="Activity count"
          :options="options"
        />
      </el-form-item>
      <el-form-item label="学校" prop="school">
        <el-input class="w-50 m-2" v-model="ruleForm.school" />
      </el-form-item>

      <el-form-item label="出生日期" required>
        <el-col :span="11">
          <el-form-item prop="birthday">
            <el-date-picker
              v-model="ruleForm.birthday"
              type="date"
              label="Pick a date"
              placeholder="Pick a date"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col class="text-center" :span="2">
          <span class="text-gray-500"></span>
        </el-col>
      </el-form-item>
      <el-form-item label="入学时间" required>
        <el-col :span="11">
          <el-form-item prop="enrollmentTime">
            <el-date-picker
              v-model="ruleForm.enrollmentTime"
              type="date"
              label="Pick a date"
              placeholder="Pick a date"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col class="text-center" :span="2">
          <span class="text-gray-500"></span>
        </el-col>
      </el-form-item>

      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="ruleForm.sex">
          <el-radio label="男" />
          <el-radio label="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="签名" >
        <el-input v-model="ruleForm.signature" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)"
          >确定</el-button
        >
        <el-button @click="resetForm(ruleFormRef)">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog v-model="centerDialogVisible" title="修改委托" width="40%" center>
    <el-form
      :model="form"
      label-width="120px"
      :rules="rules2"
      ref="ruleFormRef"
    >
      <div>接单条件</div>
      <br />

      <el-form-item label="性别">
        <el-radio-group v-model="form.sex">
          <el-radio label="男" />
          <el-radio label="女" />
          <el-radio label="不限" />
        </el-radio-group>
      </el-form-item>
      <div>委托内容</div>
      <el-form-item label="委托" prop="content">
        <el-input v-model="form.content" type="input" />
      </el-form-item>
      <el-form-item label="联系方式" prop="content">
        <el-input v-model="form.contact" type="input" />
      </el-form-item>
      <el-form-item label="地址" prop="content">
        <el-input v-model="form.address" type="input" />
      </el-form-item>
      <el-form-item label="金额" prop="money">
        <el-input v-model="form.money" type="input" />
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateCommission(ruleFormRef)"
          >确定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>
    <script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { onMounted, reactive, ref } from "vue";
import {
  ElMessage,
  ElMessageBox,
  FormInstance,
  FormRules,
  TabsPaneContext,
  UploadProps,
} from "element-plus";
import axios from "axios";
import { InfoFilled } from "@element-plus/icons-vue";
const activeName = ref("first");
const dialogTableVisible = ref(false);
const ruleFormRef = ref<FormInstance>();
const nowStudentId = sessionStorage.getItem("studentId");
const headerObj = ref({ token: sessionStorage.getItem("token") });
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
});
const form = reactive({
  name: "",
  region: "",
  sex: "",
  content: "",
  money: "",
  id: "",
  contact: "",
  address: "",
});
const centerDialogVisible = ref(false);
onMounted(() => {
  getPerson();
  getCommission();
});

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
    })
    .catch((error) => {
      ElMessage.error("查看评论失败");
    });
};
//根据id查询委托(修改委托)
const postCommission = (commissionId) => {
  centerDialogVisible.value = true;
  axios
    .post(
      "/demo/commission/getCommissionAndStudentBycommissionId",
      commissionId,
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      form.name = res.data.result.name;
      form.sex = res.data.result.sex;
      form.content = res.data.result.content;
      form.money = res.data.result.money;
      form.id = res.data.result.id;
      form.contact = res.data.result.contact;
      form.address = res.data.result.address;
    })
    .catch((error) => {
      ElMessage.error("查看评论失败");
    });
};

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event);
};
const commissions = reactive({
  commission: [],
});
//编辑资料
const updatePerson = () => {
  dialogTableVisible.value = true;
};
//修改个人资料
const submitForm = async (formEl: FormInstance | undefined) => {
  
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      axios
        .put(
          "/demo/student/updateStudentById",
          {
            name: ruleForm.name,
            sex: ruleForm.sex,
            address: ruleForm.address,
            enrollmentTime: ruleForm.enrollmentTime,
            school: ruleForm.school,
            birthday: ruleForm.birthday,
            signature: ruleForm.signature,
            age: ruleForm.age,
            id: sessionStorage.getItem("studentId"),
          },
          {
            headers: {
              token: sessionStorage.getItem("token"),
            },
          }
        )
        .then((res) => {
          dialogTableVisible.value = false;
        })
        .catch((error) => {
          ElMessage.error("修改个人信息失败");
        });
    } else {
      console.log("error submit!", fields);
    }
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
//查询自己的委托
const getCommission = () => {
  axios
    .post(
      "/demo/commission/getCommissionAndStudentByStudentId",

      sessionStorage.getItem("studentId"),
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
//根据commissionId删除委托
const deleteCommission = (commissionId) => {
  ElMessageBox.confirm("将永久删除词条委托. 是否继续?", "Warning", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      axios
        .post("/demo/commission/deleteCommissionById", commissionId, {
          headers: {
            token: sessionStorage.getItem("token"),
          },
        })
        .then((res) => {
          ElMessage({
            type: "success",
            message: "删除成功",
          });
          getCommission();
        })
        .catch((error) => {
          ElMessage.error("删除评论失败");
        });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "删除失败",
      });
    });
};

//修改委托表单
const updateCommission = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      axios
        .put(
          "/demo/commission/updateCommission",

          {
            studentId: sessionStorage.getItem("studentId"),
            limitSex: form.sex,
            content: form.content,
            money: form.money,
            id: form.id,
            contact: form.contact,
            address: form.address,
          },
          {
            headers: {
              token: sessionStorage.getItem("token"),
            },
          }
        )
        .then((res) => {
          if (res.data.msgId == "A001") {
            centerDialogVisible.value = false;
            getCommission();
          }
          if (res.data.msgId == "A002") {
            ElMessage.error("修改失败");
          }
        })
        .catch((error) => {
          ElMessage.error("修改失败");
        });
    } else {
      console.log("error submit!", fields);
    }
  });
};

const imageUrl = ref("");
//照片
//上传成功(修改头像)
const handleAvatarSuccess: UploadProps["onSuccess"] = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!);
  axios
    .put(
      "/demo/student/updateAvatarByStudentId",
      {
        avatar: response.result,
        studentId: sessionStorage.getItem("studentId"),
        oldAvatar: ruleForm.avatar,
      },

      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      commissions.commission = res.data.result;
      getPerson();
    })
    .catch((error) => {
      ElMessage.error("查询委托失败");
    });
};

const beforeAvatarUpload: UploadProps["beforeUpload"] = (rawFile) => {
  if (rawFile.type !== "image/jpeg") {
    ElMessage.error("Avatar picture must be JPG format!");
    return false;
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error("照片大小不能超过2MB!");
    return false;
  }
  return true;
};
const resetForm2 = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};
const rules = reactive<FormRules>({
  name: [
    { required: true, message: "昵称不能为空", trigger: "blur" },
  
  ],
  age: [
    {
      required: true,
      message: "请选择年龄",
      trigger: "change",
    },
  ],
  count: [
    {
      required: true,
      message: "Please select Activity count",
      trigger: "change",
    },
  ],
  birthday: [
    {
      type: "date",
      required: true,
      message: "请选择出生日期",
      trigger: "change",
    },
  ],
  enrollmentTime: [
    {
      type: "date",
      required: true,
      message: "请选择入学时间",
      trigger: "change",
    },
  ],
  type: [
    {
      type: "array",
      required: true,
      message: "Please select at least one activity type",
      trigger: "change",
    },
  ],
  resource: [
    {
      required: true,
      message: "Please select activity resource",
      trigger: "change",
    },
  ],
  desc: [
    { required: true, message: "Please input activity form", trigger: "blur" },
  ],
  address: [
    { required: true, message: "宿舍公寓不能为空", trigger: "blur" },
  ],
  phone: [
    { required: true, message: "手机号不能为空", trigger: "blur" },
  ],
});

const rules2 = reactive<FormRules>({
  name: [
    { required: true, message: "Please input Activity name", trigger: "blur" },
    { min: 3, max: 5, message: "Length should be 3 to 5", trigger: "blur" },
  ],
  region: [
    {
      required: true,
      message: "Please select Activity zone",
      trigger: "change",
    },
  ],
  count: [
    {
      required: true,
      message: "Please select Activity count",
      trigger: "change",
    },
  ],

  date2: [
    {
      type: "date",
      required: true,
      message: "Please pick a time",
      trigger: "change",
    },
  ],
  type: [
    {
      type: "array",
      required: true,
      message: "Please select at least one activity type",
      trigger: "change",
    },
  ],
  sex: [
    {
      required: true,
      message: "请选择条件",
      trigger: "change",
    },
  ],
  money: [{ required: true, message: "请填写委托金", trigger: "blur" }],
  content: [{ required: true, message: "请填写委托内容", trigger: "blur" }],
});
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