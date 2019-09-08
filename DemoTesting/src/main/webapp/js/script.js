//Backbone Model

var Blog = Backbone.Model.extend({
	urlRoot:"/ajaxController",
	defaults:{
		author:'',
		title:'',
		url:''
		
	}
	
});


//Backbone Collection
var Blogs = Backbone.Collection.extend({
	url	:	function(){
				return "/getData";
			},
	model: Blog,
});

//instantiate a collection
var blogs = new Blogs();


//Backbone Views for one blog

var BlogView = Backbone.View.extend({
	model: new Blog(),
	tagName:'tr',
	
	initialize: function(){
		this.template = _.template($(".blogs-list-template").html());
	},
	events:{
		'click .edit-blog': 'edit',
		'click .update-blog':'update',
		'click .cancel':'cancel',
		'click .delete-blog':'delete'
	},
	
	edit:function(){
		$('.edit-blog').hide();
		$('.delete-blog').hide();
		this.$('.update-blog').show();
		this.$('.cancel').show();
		
		var author = this.$('.author').html();
		var title = this.$('.title').html();
		var url = this.$('.url').html();
		
		this.$('.author').html('<input type="text" class="form-control author-update" value="'+ author+ '">');
		this.$('.title').html('<input type="text" class="form-control title-update" value="'+ title+ '">');
		this.$('.url').html('<input type="text" class="form-control url-update" value="'+ url+ '">');
	},
	
	update: function(){
//		this.model.set('author', $('.author-update').val());
//		this.model.set('title', $('.title-update').val());
//		this.model.set('url', $('.url-update').val());
		
		this.model.set({
			author: $('.author-update').val(),
			title: $('.title-update').val(),
			url: $('.url-update').val()
		});
		this.model.save(this.model.changed, {patch: true});
	},
	
	cancel:function(){
		blogsView.render();
	},
	
	delete: function(){
		this.model.destroy();
	},
	
	render:function(){
		this.$el.html(this.template(this.model.toJSON()));
		
		return this;
	}
	
});

//Backbone Views for one All

var BlogsView =  Backbone.View.extend({
	collection:blogs,
	el:$('.blogs-list'),
	initialize:function(){
		var self = this;
		this.collection.on('add', this.render, this);
		this.collection.on('change', this.render, this);
		
		this.collection.on('remove', this.render, this);
		
		this.render();
	},
	render: function(){
		console.log(Math.random());
		var self = this;
		this.$el.html('');
		_.each(this.collection.toArray(), function(blog){
			self.$el.append((new BlogView({model:blog})).render().$el);
		});
		return this;
		
		
	}
	
});

blogs.fetch({
	success: function(){
		console.log('data recived on first call');
		
		var blogsView = new BlogsView();
	}
});

$(document).ready(function(){
	$('.add-blog').on('click', function(){
		var blog = new Blog({
			author: $('.author-input').val(),
			title:$('.title-input').val(),
			url:$('.url-input').val()
		});
		console.log(blog);
		 $('.author-input').val('');
		 $('.title-input').val('');
		$('.url-input').val('');
		
		blog.save();
		
		blogs.add(blog);
		
		
	})
})