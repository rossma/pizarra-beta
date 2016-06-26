<template>
  <div>
    <header class="navbar navbar-inverse navbar-static-top">
      <div class="container">
        <a v-link="'/home'" class="navbar-brand">Pizarra</a>
        <ul class="nav navbar-nav pull-right">
          <li><a v-link="'/add'">Add</a></li>
        </ul>
        <form class="navbar-form navbar-right">
          <input type="text" class="form-control" placeholder="Buscar...">
        </form>
      </div>
    </header>
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>