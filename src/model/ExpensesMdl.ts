
// This allows to deepClone an item including its nested references.
// Curtasy of https://javascript.plainenglish.io/deep-clone-an-object-and-preserve-its-type-with-typescript-d488c35e5574
export class cloneable {
    public static deepCopy<T>(source: T): T {
      return Array.isArray(source)
      ? source.map(item => this.deepCopy(item))
      : source instanceof Date
      ? new Date(source.getTime())
      : source && typeof source === 'object'
            ? Object.getOwnPropertyNames(source).reduce((o, prop) => {
               Object.defineProperty(o, prop, Object.getOwnPropertyDescriptor(source, prop)!);
               o[prop] = this.deepCopy((source as { [key: string]: any })[prop]);
               return o;
            }, Object.create(Object.getPrototypeOf(source)))
      : source as T;
    }
  }

export class ExpenseCategory {
    title:string = ""
    color:string = "white"

    constructor(_name:string, _color:string){
        this.title = _name;
        this.color = _color;
    }
}


export class ExpenseChild {
    category:ExpenseCategory=new ExpenseCategory("","")
    amount:number = 0
}

export class ExpenseItem {
    description:string = "Default description"
    date:string = ""
    user:string = ""
    id:number=-1
    child:ExpenseChild[] = []

    private _totalAmount:number=0.

    constructor(amount:number, description:string, date:string, category:ExpenseCategory, user:string, id:number){
        this.description = description
        this.date = date
        this.id = id
        this.user = user

        let expenseChild = new ExpenseChild()
        expenseChild.amount = amount
        expenseChild.category = category
        this.child.push(expenseChild)
        this._totalAmount = amount
    }

    copyInto(expense:ExpenseItem){
        this.description = expense.description
        this.date = expense.date
        this.id = expense.id
        this.user = expense.user
        this._totalAmount = expense._totalAmount
        
        for(let itm of this.child){
            this.child.push(itm)
        }
    }

    dupplicate(){
        let expense = new ExpenseItem(this._totalAmount,this.description,this.date,this.child[0].category, this.user, this.id)
        expense.child = []

        for(let i of this.child)
        {
            expense.child.push(i)
        }
        expense.refreshTotalAmount()

        return expense
    }

    addChildExpense(expense:ExpenseChild){
        this.child.push(expense)
        this._totalAmount += expense.amount
    }

    removeChildExpense(index:number){
        this.child = this.child.filter((expenseItem, ind) => ind !== index)
        this._totalAmount = this.getChildAmount()
    }

    refreshTotalAmount(){
        this._totalAmount = this.getChildAmount()
    }

    getTotalAmount() {
        return this._totalAmount
    }

    private getChildAmount(){
        let num:number = 0
        for(let item of this.child){
            num = (num + item.amount)
        }

        console.log("Result " + num)

        return num

    }
}

export default class ExpensesModel {
    private _items: ExpenseItem[] = []
    private _categories: ExpenseCategory[] = []
    private _users: string[] = []

    public set items(its:ExpenseItem[]){
        this._items = its
    }

    public get items(){
        return this._items;
    }

    public set users(users:string[]){
        this._users = users
    }

    public get users() {
        return this._users;
    }

    public get categories() {
        return this._categories;
    }

    set categories(categories:ExpenseCategory[]){
        this._categories = categories
    }
}   