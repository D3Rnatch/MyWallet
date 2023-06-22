
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
}


export class ExpenseChild {
    category:ExpenseCategory=new ExpenseCategory()
    amount:number = 0
} 

export class ExpenseItem {
    description:string = ""
    date:string = ""
    user:string = ""
    id:number=-1
    totalAmount:number=0
    child:ExpenseChild[] = []
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