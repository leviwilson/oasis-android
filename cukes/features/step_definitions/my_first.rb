include CalabashDriver

When /^I choose to view the Podcasts$/ do
  go_to_podcasts
end

Then /^the most recent podcasts are displayed$/ do
  press_item 5
end

def go_to_podcasts()
  CalabashDriver.click_on 'home_btn_podcast'
end
