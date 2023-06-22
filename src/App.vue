<template>
  <v-app>
    <v-toolbar color="primary">
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
      <v-toolbar-title>Account Name</v-toolbar-title>
    </v-toolbar>
    <v-layout>
      <v-navigation-drawer
        expand-on-hover
        rail
      >
        <v-list>
          <v-list-item
            prepend-avatar="https://randomuser.me/api/portraits/women/85.jpg"
            title="My Fancy Account"
          ></v-list-item>
        </v-list>

        <v-divider></v-divider>

        <v-list density="compact" nav>
          <v-list-item prepend-icon="mdi-folder" title="Account" value="myfiles"></v-list-item>
          <v-list-item prepend-icon="mdi-account-multiple" title="Account dashboard" value="shared"></v-list-item>
          <v-list-item prepend-icon="mdi-star" title="Export" value="starred"></v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-main>
        <UIAccount />
<!--
        <v-layout>
        </v-layout>
      <v-expansion-panels>
        <v-expansion-panel>
          <v-expansion-panel-title>
            Edit Expenses
          </v-expansion-panel-title>
          <v-expansion-panel-text>
            <template v-slot:default>
              <UIExpenseDetail :expenseItem='expenseItm.item' :categories="model.categories" :users="model.users" @save-changes="itemUpdated" @delete-item="itemDeleted"/>
            </template>
          </v-expansion-panel-text>
        </v-expansion-panel>

      </v-expansion-panels> -->
    </v-main>
    </v-layout>
  </v-app>
</template>

<script setup lang="ts">
  import UIAccount from '@/screens/UIAccount.vue'
  import { ExpenseCategory, ExpenseChild, ExpenseItem } from "@/model/ExpensesMdl"
  import { ExpensesLogic } from '@/model/ExpensesLogic'
  import { reactive, ref } from 'vue'
  import { useExpenseModelStore } from '@/stores/Stores'

  const model = useExpenseModelStore().expenseModel


  const drawer = ref(false)
  const items = ref([{
    title:'Expense List',
    value:'expense_list'
  },
  {
    title:'Add Expense',
    value:'add_expense'
  },
  {
    title:'Account Summary',
    value:'account_summary'
  },
  {
    title:'Account parameters',
    value:'account_parameters'
  } ])

  let users = [
    "me",
    "amanda",
    "simon"
  ];
  model.users = users;

  let categories:ExpenseCategory[] = [];
  let cat1 = new ExpenseCategory()
  cat1.title = "House"
  cat1.color = "red"
  categories.push(cat1);

  let cat2 = new ExpenseCategory()
  cat2.title = "Restaurants"
  cat2.color = "purple"
  categories.push(cat2);

  model.categories = categories;

  console.log("Init has generated " + model.users.length + "users and " + model.categories.length + " categories")

  let logic = new ExpensesLogic()

  for(let i=0; i < 10; ++i)
  {
    let exp = new ExpenseItem();
    logic.setItem(exp)

    exp.description = "Some basic description"
    exp.date="2023/10/12"
    exp.user = users[0]
    exp.id = i

    for(let j=i; j<3; ++j)
    {
        let chd = new ExpenseChild()
        chd.category = cat1
        chd.amount = -30 + (1*j)
        logic.addChildExpense(chd)
    }
    model.items.push(exp)
  }

  const expenseItm = reactive({ item: model.items[0]})

  function itemUpdated(item:ExpenseItem)
  {
    // callback function returning the boolean value
    function callback_func(object: ExpenseItem): boolean 
    {
        // if the object color is green, return true; otherwise, return false for a particular object element
        if (object.id === item.id) {
            return true;
        }
        return false;
    }

    // store searched object, returned from the find() method
    let searched_obj: number | undefined = model.items.findIndex(callback_func);

    model.items[searched_obj] = item
    state.value = Direction.List
  }

  function onEditItem(index:number)
  {
    expenseItm.item = model.items[index]
    state.value = Direction.Add
  }

  function itemDeleted(item:ExpenseItem)
  {
    model.items = model.items.filter(obj => item.id !== obj.id)
    state.value = Direction.List
  }


</script>
