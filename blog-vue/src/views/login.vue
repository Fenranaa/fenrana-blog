<template>
  <div class="login">
    <div class="login-center">
      <div class="aaa"></div>
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        class="login-from"
      >
        <el-form-item prop="username">
          <el-input type="text" v-model="ruleForm.username" autocomplete="off">
            <i slot="prefix" class="el-input__icon el-icon-user-solid"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="pass" style="margin-top: 35px">
          <el-input
            v-bind:type="passwordType"
            v-model="ruleForm.pass"
            autocomplete="off"
          >
            <i slot="prefix" class="el-input__icon el-icon-lock"></i>
            <i
              @click="viewPassword"
              slot="suffix"
              class="el-input__icon el-icon-view"
              style="cursor: pointer"
            ></i>
          </el-input>
        </el-form-item>

        <el-form-item class="login-button">
          <el-button type="primary" style="width: 128px;" @click="submitForm()"
            >登录</el-button
          >
          <el-button style="width: 128px;" @click="resetForm('ruleForm')"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { postRequest } from "../utils/request";
import Cookies from "js-cookie";

export default {
  name: "login",
  data() {
    var checkUserName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("用户名不能为空!"));
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      }
    };
    return {
      ruleForm: {
        pass: "",
        username: ""
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        username: [{ validator: checkUserName, trigger: "blur" }]
      },
      passwordType: "password"
    };
  },
  methods: {
    submitForm() {
      if (this.ruleForm.username === "" && this.ruleForm.pass === "") {
        this.$message({
          message: "用户名和密码不能为空",
          type: "warning"
        });
      } else {
        let parm = new URLSearchParams();
        parm.append("username", this.ruleForm.username);
        parm.append("password", this.ruleForm.pass);
        postRequest("/login", parm)
          .then(response => {
            window.console.log(response.data);
            if (response.data.code === 200) {
              Cookies.set("loginToken", response.data.data, {
                expires: 2,
                path: ""
              });
              this.$message({
                message: "登录成功",
                type: "info"
              });
              let path = this.$route.query.redirect;
              if (path === "" && path === undefined) {
                path = "/admin";
              }
              this.$router.push({
                path: path
              });
            } else {
              window.console.log(1111);
              this.$message({
                message: response.data.msg,
                type: "warning"
              });
            }
          })
          .catch(error => {
            window.console.log(error);
          });
      }
    },
    resetForm(formName) {
      window.console.log(this.$route.query.redirect);
      this.$refs[formName].resetFields();
    },
    viewPassword() {
      window.console.log("1111");
      if (this.passwordType === "text") {
        this.passwordType = "password";
      } else {
        this.passwordType = "text";
      }
    }
  }
};
</script>

<style scoped lang="less">
.login {
  height: 100%;
  width: 100%;
  background: #42b983;
  padding-top: 160px;
  .login-center {
    border-top: 10px solid #409eff;
    width: 320px;
    height: 350px;
    background: #ffffff;
    margin: auto;
    border-radius: 8px;
    .login-from {
      padding-top: 40px;
      padding-left: 10px;
      padding-right: 10px;
      .login-button {
        margin-top: 40px;
        text-align: center;
      }
    }
  }
}
</style>
