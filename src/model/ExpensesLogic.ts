import "./ExpensesMdl"
import { ExpenseItem, ExpenseChild, ExpenseCategory } from "./ExpensesMdl"

    export class ExpensesLogic
    {
        private _item:ExpenseItem = new ExpenseItem()
        
        public setItem(itm:ExpenseItem)
        {
            this._item = itm
        }

        public addChildExpense(expense:ExpenseChild)
        {
            this._item.child.push(expense)
            this._item.totalAmount += expense.amount
        }

        public removeChildExpense(index:number)
        {
            this._item.child = this._item.child.filter((expenseItem, ind) => ind !== index)
            this.updateTotalAmount()    
        }

        public updateTotalAmount()
        {
            this._item.totalAmount = this.getChildTotalAmount()
        }


        copyInto(expense:ExpenseItem){
            this._item.description = expense.description
            this._item.date = expense.date
            this._item.id = expense.id
            this._item.user = expense.user
            this._item.totalAmount = expense.totalAmount
            
            for(let itm of expense.child){
                this._item.child.push(itm)
            }

            this.updateTotalAmount()
        }
    
        dupplicate(){
            let expense = new ExpenseItem()
            let logic = new ExpensesLogic()
            logic.setItem(expense)
            logic.copyInto(this._item)
    
            return expense
        }    

        private getChildTotalAmount(){
            let num:number = 0
            for(let item of this._item.child){
                num = (num + item.amount)
            }
            return num
        }
    }
