module CalabashDriver
  include Calabash::Android::Operations

  def click_on(id)
    performAction 'click_on_view_by_id', id
  end

  def press_item(n)
    performAction 'press_list_item', n, 0
  end

end
