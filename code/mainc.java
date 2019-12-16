package WTE;
import java.util.*;
public class mainc {
private List<User> user;
private List<Dish> dish;

public mainc(/*User p[],Dish c[]*/) {		//主要功能类
	
	user = new ArrayList<>();
	dish = new ArrayList<>();
	//从数据库读出所有User和Dish
	/*for(int i=0;i<c.length;i++)
		dish.add(c[i]);
	for(int j=0;j<p.length;j++)
		user.add(p[j]);*/
	
}

public User login(Evaluate evaluate) {		//注册
	User new_user = new User(evaluate.getid(),0,0,0,0,0);
	user.add(new_user);
	update(evaluate);
	new_user = user.get(user.size()-1);
	return new_user;
}

public int[] recommend(User one,int k) {	//推荐
	one.d_sort(dish);
	return one.recommend(k);
}

public void update(Evaluate evaluate) {		//反馈更新
	evaluate.updatepoints(dish);
	update one = new update(user,dish);
	one.update_user(evaluate);
	one.resolve(user, dish);
	//将所有User和Dish存回数据库
}


}
