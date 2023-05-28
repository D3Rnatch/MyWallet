<template>
  <v-app>
    <v-main>
      <UIExpenseDetail ref="detail" :expenseItem='expenseItm.item' :categories="model.categories" :users="model.users" @save-changes="itemUpdated" @delete-item="itemDeleted"/>
      <UIExpenseList @edit-item="onEditItem"/>
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
  import UIExpenseList from '@/components/UIExpenseList.vue'
  import UIExpenseDetail from '@/components/UIExpenseDetail.vue'
  import { ExpenseCategory, ExpenseChild, ExpenseItem } from "@/model/ExpensesMdl"
  import { reactive, watch } from 'vue'
  import { useExpenseModelStore } from '@/stores/Stores'

  const model = useExpenseModelStore().expenseModel

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

  for(let i=0; i < 10; ++i)
  {
    let exp = new ExpenseItem(-130, "Some basic description", "2023/10/12", cat1, users[0], i);
    for(let j=i; j<3; ++j)
    {
        let chd = new ExpenseChild()
        chd.category = cat1
        chd.amount = -30 + (1*j)
        exp.addChildExpense(chd)
    }
    model.items.push(exp)
  }

  const expenseItm = reactive({ item: model.items[0]})



  function itemUpdated(item:ExpenseItem)
  {

    /*for(let i=0; i < model.items.length; ++i)
    {
        if(model.items[i].id === item.id)
        {
          model.items[i] = item
          break;
        }
    }*/
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
  }

  function onEditItem(index:number)
  {
    console.log("Edit the property item prev " + expenseItm.item.id)
    expenseItm.item = model.items[index]
    console.log("Edit the property item after " + expenseItm.item.id)
  }

  function itemDeleted(item:ExpenseItem)
  {
    model.items = model.items.filter(obj => item.id !== obj.id)
  }


  watch(expenseItm, (itm) => {
    console.log("Has changed itm " + expenseItm.item.id)
  })

</script>
