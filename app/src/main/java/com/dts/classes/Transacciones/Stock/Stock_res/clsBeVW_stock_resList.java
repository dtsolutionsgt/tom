package com.dts.classes.Transacciones.Stock.Stock_res;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeVW_stock_resList {
    @ElementList(inline=true,required = false)
    public List<clsBeVW_stock_res> items = new List<clsBeVW_stock_res>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeVW_stock_res> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeVW_stock_res clsBeVW_stock_res) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeVW_stock_res> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeVW_stock_res> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeVW_stock_res get(int index) {
            return null;
        }

        @Override
        public clsBeVW_stock_res set(int index, clsBeVW_stock_res element) {
            return null;
        }

        @Override
        public void add(int index, clsBeVW_stock_res element) {

        }

        @Override
        public clsBeVW_stock_res remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeVW_stock_res> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeVW_stock_res> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeVW_stock_res> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}