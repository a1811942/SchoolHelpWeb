<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="1200px">Aside</el-aside>
      <el-main>
        <el-card class="box-card">
          <div style="text-align: center">大学生专属平台</div>

          <el-form
            ref="ruleFormRef"
            :model="ruleForm"
            :rules="rules"
            label-width="120px"
            class="demo-ruleForm"
            :size="formSize"
            status-icon
            style="text-align: center"
          >
            <el-form-item label="用户名" prop="username">
              <el-input v-model="ruleForm.username" />
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input v-model="ruleForm.password" type="password" />
            </el-form-item>

            <el-form-item label="确认密码" prop="checkPassword">
              <el-input  v-model="ruleForm.checkPassword" type="password" />
            </el-form-item>

            <el-form-item label="姓名" prop="name">
              <el-input v-model="ruleForm.name" />
            </el-form-item>

            <el-form-item label="学校" prop="school">
              <el-input v-model="ruleForm.school" />
            </el-form-item>

            <el-form-item label="入学时间" required>
              <el-form-item prop="enrollmentTime">
                <el-date-picker
                  v-model="ruleForm.enrollmentTime"
                  type="date"
                  label="Pick a date"
                  style="width: 100%"
                />
              </el-form-item>
            </el-form-item>

            <el-form-item label="出生日期" required>
              <el-form-item prop="birthday">
                <el-date-picker
                  v-model="ruleForm.birthday"
                  type="date"
                  label="Pick a date"
                  style="width: 100%"
                />
              </el-form-item>
            </el-form-item>

            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="ruleForm.sex">
                <el-radio label="男" />
                <el-radio label="女" />
              </el-radio-group>
            </el-form-item>

            <el-form-item label="邮箱" prop="email">
              <el-input v-model="ruleForm.email" />
            </el-form-item>
            <el-form-item label="验证码" prop="code">
              <el-input v-model="ruleForm.code" />
            </el-form-item>
            <el-button type="primary" @click="email">获取验证码</el-button>

            <el-button type="primary" @click="submitForm(ruleFormRef)"
              >注册</el-button
            >
            <el-button @click="resetForm(ruleFormRef)">取消</el-button>
          </el-form>
        </el-card>
      </el-main>
    </el-container>
    <!-- 外面评论对话框 -->
    <el-dialog v-model="dialogTableVisible" title="评论">
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
  </div>
</template>
<script lang="ts" setup>
import { useRouter } from "vue-router";
import axios from "axios";
import { ElMessage } from "element-plus";
import { reactive, ref, registerRuntimeCompiler } from "vue";
import type { FormInstance, FormRules } from "element-plus";
const username = ref("");
const password = ref("");
const router = useRouter();
const formSize = ref("default");
const ruleFormRef = ref<FormInstance>();
const ruleForm = reactive({
  username: "",
  password: "",
  checkPassword: "",
  name: "",
  enrollmentTime: "",
  email: "",
  sex: "",
  code: "",
  birthday: "",
  school: "",
});
// 自定义表单验证
//密码
const validatePass = (rule: any, value: any, callback: any) => {
  const check = /[a-zA-Z]{1}\S{5,11}/;
  if (value === "") {
    callback(new Error("密码不能为空"));
  } else if (check.test(value)) {
    return callback();
  } else {
    callback(new Error("长度在6-10之间"));
  }
};
//确认密码
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("确认密码"));
  } else if (ruleForm.password == value) {
    return callback();
  } else {
    callback(new Error("密码不同"));
  }
};
//用户名
const usernamePass = (rule: any, value: any, callback: any) => {
  const check = /[a-zA-Z]{1}\S{5,21}/;
  const check2 = /^[a-zA-Z0-9_]{0,}$/;
  if (value === "") {
    callback(new Error("用户名不能为空"));
  } else if (!check.test(value)) {
    callback(new Error("长度在6-20之间"));
  } else if (!check2.test(value)) {
    callback(new Error("不能使用汉字和特殊符号"));
  } else {
    callback();
  }
};
const rules = reactive<FormRules>({
  username: [
    {
      validator: usernamePass,
      trigger: "blur",
    },
  ],
  password: [
    {
      validator: validatePass,
      trigger: "blur",
    },
  ],
  checkPassword: [
    {
      validator: validatePass2,
      trigger: "blur",
    },
  ],
  name: [
    {
      required: true,
      message: "姓名不能为空",
      trigger: "blur",
    },
  ],
  school: [
    {
      required: true,
      message: "学校不能为空",
      trigger: "blur",
    },
  ],
  code: [
    {
      required: true,
      message: "验证码不能为空",
      trigger: "blur",
    },
  ],
  enrollmentTime: [
    {
      type: "date",
      required: true,
      message: "入学时间不能为空",
      trigger: "change",
    },
  ],
  birthday: [
    {
      type: "date",
      required: true,
      message: "出生日期不能为空",
      trigger: "change",
    },
  ],
  sex: [
    {
      required: true,
      message: "请选择你的性别",
      trigger: "change",
    },
  ],
  email: [
    {
      required: true,
      message: "邮箱不能为空",
      trigger: "blur",
    },
  ],
});
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      axios
        .post("/demo/student/register", {
          code: ruleForm.code,
          student: ruleForm,
        })
        .then((res) => {
          if (res.data.msgId == "A001") {
            ElMessage.success({
              message: "注册成功",
              type: "success",
            });

            router.push({
              path: "/login",
            });
          } else if (res.data.msgId == "A002") {
            ElMessage.error("验证码错误");
          } else if (res.data.msgId == "A003") {
            ElMessage.error("邮箱已被绑定");
          }
        })
        .catch((error) => {
          ElMessage.error("注册失败");
        });
    } else {
      console.log("error submit!", fields);
    }
  });
};

const email = () => {
  axios
    .post("/demo/student/email", { email: ruleForm.email })
    .then((res) => {
      if (res.data.msgId == "A001") {
        ElMessage.success({
          message: "验证码已发送",
          type: "success",
        });
      }
      if (res.data.msgId == "A002") {
        ElMessage.error("邮箱已被注册");
      }
      if (res.data.msgId == "A003") {
        ElMessage.error("邮箱错误");
      }
    })
    .catch((error) => {
      ElMessage.error("邮箱错误");
    });
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
  router.push({
          path: "/login",
        });
};

const options = Array.from({ length: 10000 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}));
</script>
    <style scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 400px;
}
</style>