<template>
  <div>
      <Header />
    <div class="content-box">
      <div class="content">
<router-view></router-view>
      </div>
    </div>
  </div>
</template>
<script setup>
import Header from "../components/Header.vue";
</script>
<style scoped>
.content-box {
  position: absolute;
  left: 250px;
  right: 0;
  top: 0px;
  bottom: 0px;
  padding-bottom: 0px;
  -webkit-transition: left 0.3s ease-in-out;
  transition: left 0.3s ease-in-out;
  background: #ffffff;
}

.content-collapse {
  left: 65px;
}

.Asid {
  width: 100%;
  position: fixed;
  height: 100%;
}

.content {
  width: auto;
  height: 99%;
  padding: 0px;
  box-sizing: border-box;
  background: #ffffff;
  overflow-y: auto;
}
</style>

