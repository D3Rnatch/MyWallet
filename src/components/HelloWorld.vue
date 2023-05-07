
<script setup lang="ts">
    import { ref } from 'vue'
    import { reactive } from 'vue'
    import ExpensesModel from "../model/ExpensesMdl"
    import ExpenseItem from "../model/ExpensesMdl"

    const emit = defineEmits<{(e: 'deleteRequest', item: ExpenseItem): void }>()

    const model = reactive(new ExpensesModel());
    const headers= ref([
          { title: 'Category', key: 'category' },
          { title: 'Date', key: 'date' },
          { title: 'Description', key: 'description' },
          { title: 'Amount (€)', key: 'amount' },
          { title: 'User', key: 'user'},
          { title: 'Action', key: ''}
        ]);
  
    function getColor(amount:number) {
      if(amount < 0.) return "red"
      else if(amount >= 0. ) return "green"
    }


    function deleteItem(item:ExpenseItem){
    }

    function rowClick(item:any, row:any) {      
      row.select(true);
      //item.name - selected id
    }

</script>


<template>
  <v-data-table
    :headers="headers"
    :items="model.getItems()"
    item-key="name"
    class="elevation-1"
    items-per-page="-1"

    @click:row="rowClick"
    hide-default-footer
    hover
    single-select
    >



    <template v-slot:item.amount="{ item }">
      <v-chip :color="getColor(item.raw.amount)">
        {{ item.raw.amount }}
      </v-chip>
    </template>

    <template v-slot:item.category="{ item }">
      <v-chip :color="item.raw.category.color">
        {{ item.raw.category.title }}
      </v-chip>
    </template>
  </v-data-table>
</template>

<style>
  tr.v-data-table__selected {
    background: #7d92f5 !important;
  }
</style>
