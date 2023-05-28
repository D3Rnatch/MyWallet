<script setup lang="ts">
    import { useExpenseModelStore } from '../stores/Stores'

    const emit = defineEmits<{
            (e: 'edit-item', index: number): void,
    }>()


    const model = useExpenseModelStore().expenseModel
  
    function getColor(amount:number) {
      if(amount < 0.) return "red"
      else if(amount >= 0. ) return "green"
    }

    function editBtnClick(index:number) {
      emit('edit-item', index)     
    }
</script>


<template>
 <v-table
  hover
  single-select
 >
  <thead>
    <tr>
        <th class="text-left">
          Date
        </th>
        <th class="text-left">
          Description
        </th>
        <th class="text-left">
          Amount(€)
        </th>
      </tr>
  </thead>
  <tbody>
    <tr v-for="(item, index) in model.items">
      <td>{{ item.date }}</td>
      <td>{{ item.description }}</td>
      <td>      
        <v-chip :color="getColor(item.getTotalAmount())">
          {{ item.getTotalAmount() }}
        </v-chip>
      </td>
      <td>
        <v-icon
         size="small"
         class="me-2"
         @click="editBtnClick(index)"
        >
        mdi-pencil
        </v-icon>
      </td>
    </tr>
  </tbody>
 </v-table>
</template>
