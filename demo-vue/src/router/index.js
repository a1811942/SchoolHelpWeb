import {
	createRouter,
	createWebHistory
} from 'vue-router'
import Login from '../views/login.vue'
import Register from '../views/register.vue'
import Home from '../views/home.vue'
import List from '../views/forum/list.vue'
import Forum_publish from '../views/forum/forum_publish.vue'
import User from '../views/user.vue'
import Moments from '../views/moments2.vue'
import Information from '../views/information2.vue'
import Commission from '../views/commission.vue'
import Person from '../views/person.vue'
import Forum from '../views/forum.vue'
import Chat from '../views/person/chat.vue'
import PersonInformation from '../views/person/person_information.vue'
const routes = [{
	path: '/',
	redirect: '/login'
},
{
	path: '/login',
	name: 'Login',
	component: Login
},
{
	path: '/register',
	name: 'Register',
	component: Register
},



{
	path: '/home',
	name: 'Home',
	component: Home,
	redirect: '/home/list',
	children: [

		{
			path: '/home/list',
			component: List
		},
		{
			path: '/home/user',
			component: User
		},
		{
			path: '/home/moments',
			component: Moments
		},
		{
			path: '/home/information',
			component: Information
		},
		{
			path: '/home/commission',
			component: Commission
		},
		{
			path: '/home/person',
			name: 'Person',
			component: Person,
			children:[
				{
					path: '/home/person/person_information',
					component: PersonInformation
				},
		
			]

		},
		{
			path: '/home/forum',
			component: Forum,
			
		},
		{
			path: '/home/forum/forum_publish',
			component: Forum_publish
		},

		{
			path: '/home/person/chat',
			component: Chat
		},

	]
}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})

export default router
