<template class="page-box" >
  <div >
    <el-container>
      <!-- 头部卡片 -->
      <el-header width="100%">
        <el-card class="box-card1" shadow="hover">
          <el-container>
            <el-aside width="500px">
              <div class="top-card">
                <el-form :inline="true"> 委托 </el-form>
              </div>
            </el-aside>
            <el-container>
              <el-header>
                <el-button @click="postCommission">发布委托</el-button>
                <el-select
                  v-model="value.status"
                  class="m-2"
                  placeholder="未被抢"
                  size="large"
                  clearable="true"
                >
                  <el-option
                    v-for="item in statusName"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  /> </el-select
                >&nbsp;
                <el-select
                  v-model="value.orderType"
                  class="m-2"
                  placeholder="排序"
                  size="large"
                  clearable="true"
                >
                  <el-option
                    v-for="i in orderType"
                    :key="i.value"
                    :label="i.label"
                    :value="i.value"
                  />
                </el-select>
                &nbsp;
                <el-button @click="getCommissionAndStudent">查询</el-button>
                &nbsp;&nbsp;&nbsp;
                <el-input
                  :suffix-icon="Search"
                  placeholder="搜索动态"
                  size="large"
                  class="headerInput"
                />
              </el-header>
            </el-container>
          </el-container>
        </el-card>
      </el-header>

      <el-container>
        <el-aside width="1200px">
          <div
            v-for="o in commissions.commission"
            :key="o"
            class="infinite-list-item"
          >
            <el-card width="1000px">
              <el-container>
                <el-aside width="50px">
                  <!-- 头像 -->
                  <el-avatar :size="50" :src="state.circleUrl" @click="checkPerson(o.studentId)" class="avatar"
                /></el-aside>
                <el-container>
                  <el-header height="10px">
                    <div class="name">{{ o.name }}</div>
                    {{ o.date }}

                    <div
                      class="reception"
                      v-if="o.status == '0' && o.studentId !== studentId"
                    >
                      <el-popconfirm
                        confirm-button-text="确定"
                        cancel-button-text="取消"
                        :icon="InfoFilled"
                        icon-color="#626AEF"
                        title="是否确定接单?"
                        @confirm="confirmEvent(o.id)"
                        @cancel="cancelEvent"
                      >
                        <template #reference>
                          <el-button size="large" bg type="primary">
                            接单
                          </el-button>
                        </template>
                      </el-popconfirm>
                    </div>

                    <div
                      class="reception"
                      v-if="o.status == '1' && o.studentId !== studentId"
                    >
                      <el-button size="large" bg type="danger" disabled="true">
                        已被接
                      </el-button>
                    </div>
                    <div
                      class="reception"
                      v-if="o.status == '0' && o.studentId == studentId"
                    >
                      我的委托
                    </div>
                    <div
                      class="reception"
                      v-if="o.status == '1' && o.studentId == studentId"
                    >
                      我的委托
                      <el-button size="large" bg type="danger" disabled="true">
                        已被接
                      </el-button>
                    </div>
                  </el-header>
                </el-container>
              </el-container>
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
                  o.address
                }}</el-descriptions-item>
                <div v-if="o.status == '1' && o.employeeId == studentId">
                  <el-descriptions-item label="联系方式" :span="2">
                    {{ o.phone }}
                  </el-descriptions-item>
                </div>
                <div
                  v-if="
                    o.status == '0' ||
                    (o.status == '1' && o.employeeId != studentId)
                  "
                >
                  <el-descriptions-item label="联系方式" :span="2">
                    接单后可查看
                  </el-descriptions-item>
                </div>
                <el-descriptions-item label="委托" :span="4"
                  >{{ o.content }}
                </el-descriptions-item>
                <el-descriptions-item label="金额"
                  >{{ o.money }}
                </el-descriptions-item>
              </el-descriptions>
            </el-card>
            <br />
          </div>
        </el-aside>
        <el-main>

          <el-card class="right-card" >

          </el-card>

        </el-main>
      </el-container>
    </el-container>

    <el-dialog
      v-model="centerDialogVisible"
      title="发布委托"
      width="40%"
      center
    >
      <el-form
        :model="form"
        label-width="120px"
        :rules="rules"
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
        <el-form-item label="金额" prop="money">
          <el-input v-model="form.money" type="input" />
        </el-form-item>
        <!-- <el-form-item>
        <el-button type="primary" @click="submitForm">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item> -->
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm(ruleFormRef)"
            >发布</el-button
          >
        </span>
      </template>
    </el-dialog>
    <!-- 成功对话框 -->
    <el-dialog  v-model="centerDialogVisible2" width="300px" :show-close="false">
      <el-result
        icon="success"
        title="发布成功"
        sub-title=""
      >
        <template #extra>
          <el-button type="primary" @click="centerDialogVisible2=false">返回</el-button>
        </template>
      </el-result>
    </el-dialog>
  </div>
</template>
  <script setup lang="ts">
import { computed, onMounted, reactive, ref } from "vue";
import {
  Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  User,
} from "@element-plus/icons-vue";
import { Action, ElMessage } from "element-plus";
import axios from "axios";
import { ElNotification } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { policy } from "../upload/policy";
import { InfoFilled } from "@element-plus/icons-vue";
import type { FormInstance, FormRules } from "element-plus";
import router from "../router";
const ruleFormRef = ref<FormInstance>();
onMounted(() => {
  getCommissionAndStudent();
  console.log("studentId===", studentId.value);
});
const studentId = ref(sessionStorage.getItem("studentId"));
const state = reactive({
  circleUrl:
    "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
  squareUrl:
    "https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png",
  sizeList: ["small", "", "large"] as const,
});
const form = reactive({
  name: "",
  region: "",
  date1: "",
  date2: "",
  delivery: false,
  type: [],
  sex: "",
  content: "",
  money: "",
});
//点击头像查看信息
const  checkPerson=(a)=>{
  console.log("aaaaaaaaaaaaaa",a)
  router.push({
          path: "/home/person/chat",
          query: { toStudentId: a }
        });
}
//发布委托表单
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      axios
        .post(
          "/demo/commission/SaveCommission",

          {
            studentId: sessionStorage.getItem("studentId"),
            limitSex: form.sex,
            content: form.content,
            money: form.money,
          },
          {
            headers: {
              token: sessionStorage.getItem("token"),
            },
          }
        )
        .then((res) => {
          if (res.data.msgId == "A001") {
            getCommissionAndStudent();
            centerDialogVisible.value = false;
            centerDialogVisible2.value = true;
          }
          if (res.data.msgId == "A002") {
            getCommissionAndStudent();
            ElMessage.error("发布失败");
          }
        })
        .catch((error) => {
          ElMessage.error("发布失败");
        });
    } else {
      console.log("error submit!", fields);
    }
  });
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};

//发布委托
const centerDialogVisible = ref(false);
const centerDialogVisible2 = ref(false);
const postCommission = () => {
  centerDialogVisible.value = true;
};

const confirmEvent = (id) => {
  recept(id);
};
const cancelEvent = () => {
  console.log("cancel!");
};
//按钮
const buttons = reactive({
  type: "primary",
});
//点击接单按钮
const recept = (id) => {
  axios
    .post(
      "/demo/commission/setStatusOne",

      { id: id, acceptStudentId: sessionStorage.getItem("studentId"),acceptName:sessionStorage.getItem("studentName") },
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      if (res.data.msgId == "A001") {
        getCommissionAndStudent();
        ElNotification({
          title: "接单成功",
          message: "",
          type: "success",
        });
      }
      if (res.data.msgId == "A002") {
        getCommissionAndStudent();
        ElNotification({
          title: "你的手速慢了，已被被人接单",
          message: "",
          type: "error",
        });
      }
    })
    .catch((error) => {
      ElMessage.error("接单失败");
    });
};
const commissions = reactive({
  commission: [],
});
//查询接单人信息
const getAcceptStudentByAcceptStudentId = (AcceptStudentId) => {
  axios
    .post(
      "/demo/commission/getAcceptStudentByAcceptStudentId",
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      commissions.commission = res.data.result;
      console.log("");
    })
    .catch((error) => {
      ElMessage.error("查看评论失败");
    });
};
//查询所有的委托信息
const getCommissionAndStudent = () => {
  axios
    .post(
      "/demo/commission/getCommissionAndStudent",

      value.value,
      {
        headers: {
          token: sessionStorage.getItem("token"),
        },
      }
    )
    .then((res) => {
      commissions.commission = res.data.result;
      console.log("");
    })
    .catch((error) => {
      ElMessage.error("查看评论失败");
    });
};
const value = ref({
  status: "",
  orderType: "",
});
const statusName = ref([
  {
    value: "0",
    label: "未被抢",
  },
  {
    value: "1",
    label: "已被抢",
  },
]);
const orderType = ref([
  {
    value: "descmoney",
    label: "价格最高",
  },
  {
    value: "ascmoney",
    label: "价格最低",
  },
  {
    value: "descdate",
    label: "时间最早",
  },
  {
    value: "ascdate",
    label: "时间最晚",
  },
]);
const rules = reactive<FormRules>({
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
  date1: [
    {
      type: "date",
      required: true,
      message: "Please pick a date",
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

  <style scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card1 {
  width: 90%;
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
.block {
  top: 50px;
}
.textarea {
  border: 0;
  outline: none;
}
.bold {
  font-weight: bold;
  white-space: nowrap;
}
.top-card {
  font-weight: bold;
  font-size: x-large;
}
.name {
  font-weight: bold;
  font-size: larger;
}
.comment {
  font-weight: 530;
  font-size: larger;
}
.headerInput {
  width: 400px;
  border-right: 0ch;
}
.releaseMoments1 {
  border: 0;
  width: 99%;
}
.releaseMoments2 {
  width: 99%;
}
/* 大字体 */
.font {
  font-size: large;
}
/* 数据显示 */
.el-descriptions {
  margin-top: 20px;
}
.cell-item {
  display: flex;
  align-items: center;
}
.margin-top {
  margin-top: 20px;
}
.reception {
  float: right;
  width: 100px;
  font-size: larger;
  height: 30px;
}
body{
  height: 100%;
}
.page-box{
  background-color: brown;
  height: 100%;
}
.right-card{
  position: fixed;
  height: 600px;
  width: 380px;
  top: 80px;
  color: coral;
}
.avatar{
  cursor: pointer;
}
</style>